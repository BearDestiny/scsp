package cn.beardestiny.controller;

import cn.beardestiny.feign.UserFeign;
import cn.beardestiny.handler.RedisHandler;
import cn.beardestiny.param.GossipPostItem;
import cn.beardestiny.param.PostCommentParam;
import cn.beardestiny.pojo.GossipPost;

import cn.beardestiny.pojo.User;
import cn.beardestiny.pojo.VipPost;
import cn.beardestiny.service.GossipPostService;
import cn.beardestiny.utils.*;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @Author BearDestiny
 * @Date 2023/4/16 15:51
 * @Sign “江湖夜雨十年灯”
 * @description: 表白贴控制器
 */

@Slf4j
@RestController
@RequestMapping("/gossipwall")
public class GossipPostController {

    @Resource
    private GossipPostService gossipPostService;

    @Autowired
    private FeignGetUid uidFeign;

    @Resource
    private AliyunOSSUtil aliyunOSSUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * 校园墙帖子发布
     */
    @PostMapping("/addGossipPost")
    public RCode addGossipPost(@RequestParam(value = "post_img",required = false) MultipartFile[] post_imgs,
                                                        @RequestParam("post_text") String post_text,
                                                        @RequestHeader("userToken") String userToken) throws Exception {

        //空值校验
        if( post_imgs == null && post_text == null){
            return RCode.failure("发布失败");
        }

        // 用户未登录
        if( userToken == null ){
            return RCode.failure("用户未登录");
        }

        //feign调用scsp-user，验证用户当前token状态，检查是否过期，返回uid
        String user_id = uidFeign.getUid(userToken);
        if (user_id == "000"){
            return RCode.failure("用户未登录");
        }

        //雪花生成帖子编号 pid
        SnowflakeIdWorker snow = new SnowflakeIdWorker(1);

        //查询失败
        if( user_id == null){
            return RCode.failure("uid查询失败");
        }

        //完善gossippost帖子，存储实体类
        GossipPost gossipPost = new GossipPost();
        gossipPost.setPost_id("pid"+snow.nextId());
        gossipPost.setPost_text(post_text);
        gossipPost.setUser_id( user_id );
        RCode postSqlCode = null;

        //阿里云OSS存储 帖子图片
        RCode imgSqlCode = null;
        if( post_imgs != null ){
            //用户上传了图片
            String filename = null;             //uuid格式图片名
            String contentType = null;      //图片格式
            String url = null;                      //图片url
            List<String> imgList = new ArrayList<>();            //图片sql values，方便后续插入mysql
            //循环上传OSS
            for( MultipartFile img : post_imgs ){
                filename = "img/"+gossipPost.getPost_id()+"/" + UUID.randomUUID().toString().replace("-","").toLowerCase();
                contentType = img.getContentType();
                url = aliyunOSSUtil.uploadImage(filename, img.getBytes(), contentType);
                //mysql存储图片的参数list，格式控制 (post_id, img_id)
                imgList.add("(\'"+gossipPost.getPost_id()+"\',\'"+filename+"\')");
                log.info("图片上传成功，地址为："+ url);
            }
            //oss上传成功
            if(url != null){
                //执行 帖子图片 sql持久化
                imgSqlCode = gossipPostService.insertGossipPostImgs(imgList);
            }
        }

        //用户只上传了文字
        if( post_text != null ){
            //执行 帖子 sql持久化
            postSqlCode = gossipPostService.insertGossipPost(gossipPost);
        }

        //若mysql持久化成功，Redis同步
        RCode newPostRcode = gossipPostService.selectGossipPostById(gossipPost.getPost_id());
        if(Objects.equals(newPostRcode.getCode(), "001")){
            //Redis list长度大于40，则弹出一个旧值，维持40
            Long size = redisTemplate.opsForList().size("GossipPost");
            if (size != null && size== 40){
                //左弹旧值
                redisTemplate.opsForList().rightPop("GossipPost");
                //redis插入
                String jsonPost = MAPPER.writeValueAsString(newPostRcode.getData());
                redisTemplate.opsForList().leftPush("GossipPost", jsonPost);
            }
            //Redis list长度小于40，直接插入
            String jsonPost = MAPPER.writeValueAsString(newPostRcode.getData());
            redisTemplate.opsForList().leftPush("GossipPost", jsonPost);
        }

        assert imgSqlCode != null;
        if(Objects.equals(postSqlCode.getCode(), "001") || Objects.equals(imgSqlCode.getCode(), "001")){
            return RCode.pass("发布成功");
        }
        //返回发布结果
        return  RCode.failure("发布失败");
    }


    /**
     * 发布vip主题贴
     */
    @PostMapping("/addVipPost")
    public RCode addVipPost(@RequestParam("reciver") String name,
                                                @RequestParam("img") MultipartFile img,
                                                @RequestParam("radio") String radio,
                                                @RequestHeader("userToken") String userToken) throws Exception {

        //空值校验
        if( name == null && img == null &&  radio == null){
            return RCode.failure("发布失败");
        }

        // 用户未登录
        if( userToken == null ){
            return RCode.failure("用户未登录");
        }

        //feign调用scsp-user，验证用户当前token状态，检查是否过期，返回uid
        String user_id = uidFeign.getUid(userToken);
        if (Objects.equals(user_id, "000")){
            return RCode.failure("用户未登录");
        }

        //雪花生成帖子编号 pid
        SnowflakeIdWorker snow = new SnowflakeIdWorker(1);

        //查询失败
        if( user_id == null){
            return RCode.failure("uid查询失败");
        }

        VipPost post = new VipPost();
        post.setPost_id("vipid" + snow.nextId());
        post.setSender_id(user_id);
        post.setReciver_name(name);
        post.setPost_theme(radio);

        //阿里云OSS存储 帖子图片
        RCode imgSqlCode = null;
        if( img != null ){
            //用户上传了图片
            String filename = null;             //uuid格式图片名
            String contentType = null;      //图片格式
            String url = null;                      //图片url
            //上传OSS
            filename = "img/"+post.getPost_id()+"/" + UUID.randomUUID().toString().replace("-","").toLowerCase();
            contentType = img.getContentType();
            url = aliyunOSSUtil.uploadImage(filename, img.getBytes(), contentType);
            log.info("图片上传成功，地址为："+ url);
            //oss上传成功
            if(url != null){
                post.setPost_img(filename);
                //执行 vip帖子 sql持久化
                imgSqlCode = gossipPostService.insertVipPost(post);
            }
        }

        assert imgSqlCode != null;
        if(Objects.equals(imgSqlCode.getCode(), "001")){
            return RCode.pass("发布成功");
        }
        //返回发布结果
        return  RCode.failure("发布失败");
    }


    /**
     * 帖子分页查询
     */
    @GetMapping("/getGossipPostPage")
    public RCode getGossipPostsByPage(@RequestParam("pageNum") int pageNum ) throws Exception {
        //redis查询范围为最新40个帖子数据
        if(pageNum < 6 ){
            // Redis 先查询分页帖子
            int start = (pageNum - 1) * 8;
            int end = (pageNum * 8) - 1;
            List<String> jsonList =  redisTemplate.opsForList().range("GossipPost", start, end);

            // Redis List不为空
            if( jsonList != null ){
                // 返回Redis中的帖子数据
                List<GossipPostItem> postList = MAPPER.readValue(jsonList.toString(), new TypeReference<List<GossipPostItem>>() {});
                return RCode.pass("查询成功", postList);
            }
            //Redis中没有最新40帖子数据，走数据库，然后同步
            RCode res = gossipPostService.selectGossipPostsByPage(pageNum);
            //mysql查询不为空
            if( res.getData() != null){
                //Redis同步
                RedisHandler redisHandler = new RedisHandler();
                redisHandler.afterPropertiesSet();
                //同步完毕后返回mysql查询结果
                return res;
            }
        }

        // 超出第五页，直接走数据库
        RCode res = gossipPostService.selectGossipPostsByPage(pageNum);
        //mysql查询不为空
        if( res.getData() != null ){
            return res;
        }
        // Redis 、MySQL均查询失败
        return RCode.failure("查询失败");
    }


    /**
     * 帖子模糊查询
     */
    @GetMapping("/getGossipPostLike")
    public RCode getGossipPostsByLikeWord(@RequestParam("keyword") String keyword){
        return gossipPostService.selectLikeSearchPost(keyword);
    }


    /**
     * 帖子点赞
     */
    @GetMapping("/gossipPostLike")
    public RCode likeGossipPost(@RequestParam("post_id") String post_id, @RequestHeader("userToken") String userToken){

        //feign调用scsp-user，验证用户当前token状态，检查是否过期，返回uid
        String user_id = uidFeign.getUid(userToken);
        if (user_id == "000"){
            return RCode.failure("用户未登录");
        }
        return gossipPostService.insertGossipPostLike(post_id, user_id);
    }


    /**
     * 帖子取消点赞
     */
    @GetMapping("/gossipPostLikeCancel")
    public RCode cancelLikeGossipPost(@RequestParam("post_id") String post_id,
                                      @RequestHeader(value = "userToken", required = false) String userToken){

        //feign调用scsp-user，验证用户当前token状态，检查是否过期，返回uid
        String user_id = uidFeign.getUid(userToken);
        if (user_id == "000"){
            return RCode.failure("用户未登录");
        }
        return gossipPostService.deleteGossipPostLike(post_id, user_id);
    }


    /**
     * 用户点赞了哪些帖子
     * 此接口默认用户已登录，未登录用户无法提供已点赞查看
     * 返回RCode中的Obj为 pid List<String>
     */
    @GetMapping("/userGossipPostLikeList")
    public RCode userGossipPostLikeList(@RequestHeader("userToken") String userToken){

        //feign调用scsp-user，验证用户当前token状态，检查是否过期，返回uid
        String user_id = uidFeign.getUid(userToken);
        if (user_id == "000"){
            return RCode.failure("用户未登录");
        }

        return  gossipPostService.selectGossipPostLike(user_id);
    }


    /**
     * 帖子新增评论
     */
    @PostMapping("/addPostComment")
    public RCode addComment(@RequestHeader("userToken") String userToken, @RequestBody PostCommentParam param){
        //feign调用scsp-user，验证用户当前token状态，检查是否过期，返回uid
        String user_id = uidFeign.getUid(userToken);
        if (user_id == "000"){
            return RCode.failure("用户未登录");
        }

        return gossipPostService.addPostComment(param);
    }
}
