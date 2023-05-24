package cn.beardestiny.service.impl;

import cn.beardestiny.mapper.GossipPostImgMapper;
import cn.beardestiny.mapper.GossipPostMapper;
import cn.beardestiny.param.GossipPostItem;
import cn.beardestiny.param.PostCommentParam;
import cn.beardestiny.pojo.GossipPost;
import cn.beardestiny.pojo.VipPost;
import cn.beardestiny.service.GossipPostService;
import cn.beardestiny.utils.RCode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author BearDestiny
 * @Date 2023/4/16 18:50
 * @Sign “江湖夜雨十年灯”
 * @description: 校园墙帖子服务实现类
 */
@Service
public class GossipPostServiceImpl implements GossipPostService {

    @Resource
    private GossipPostMapper gossipPostMapper;

    @Resource
    private GossipPostImgMapper gossipPostImgMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发布普通帖子
     */
    @Override
    public RCode insertGossipPost(GossipPost gossipPost) {
        //mysql存入
        int num  = gossipPostMapper.insert(gossipPost);
        if( num == 1){
            rabbitTemplate.convertAndSend("topic.ex","addPost.gossip",gossipPost.getUser_id());
            return RCode.pass("发布成功");
        }
        return RCode.failure("发布失败");
    }

    /**
     * 发布Vip帖子
     *
     * @param vipPost
     */
    @Override
    public RCode insertVipPost(VipPost vipPost) {
        int num = gossipPostMapper.insertVipPost(vipPost);
        if( num == 1){
            return RCode.pass("发布成功");
        }
        return RCode.failure("发布失败");
    }

    /**
     * 帖子分页
     * @param pageNum 页数
     */
    @Override
    public RCode selectGossipPostsByPage(int pageNum) {
        //页面帖子起始索引
        int startIndex = (pageNum - 1) * 8;

        //获取页面帖子
        List<GossipPostItem> postItemList = gossipPostMapper.selectGossipPostItemsByPage(startIndex);

        if( postItemList != null) {
            return RCode.pass("查询成功", postItemList);
        }
        return RCode.failure("查询失败");
    }

    /**
     * 数量获取最新帖子
     */
    @Override
    public RCode selectGossipPostsByNum(int num) {
        List<GossipPostItem> postItemList = gossipPostMapper.selectGossipPostItemsByNum(num);
        if( postItemList.size() > 0) {
            return RCode.pass("查询成功", postItemList);
        }
        return RCode.failure("查询失败");
    }

    /**
     * 获取模糊搜索帖子
     */
    @Override
    public RCode selectLikeSearchPost(String keyword) {
        keyword = "%" + keyword +"%";
        List<GossipPostItem> postItemList = gossipPostMapper.selectGossipPostByLikeWord(keyword);
        if( postItemList.size() > 0) {
            return RCode.pass("查询成功", postItemList);
        }
        return RCode.failure("查询为空");
    }


    /**
     * post_id获取帖子
     */
    @Override
    public RCode selectGossipPostById(String post_id) {
        GossipPostItem item = gossipPostMapper.selectGossipPostById(post_id);
        if ( item != null){
            return RCode.pass("查询成功", item);
        }
        return RCode.failure("查询失败");
    }


    /**
     * 帖子图片持久化
     */
    @Override
    public RCode insertGossipPostImgs(List<String> imgList) {
        String sql = imgList.toString().replaceAll("^\\[|\\]$","");
        int num = gossipPostImgMapper.insertPostImg(sql);
        if( num > 0  ){
            return RCode.pass("插入成功");
        }
        return RCode.failure("插入失败");
    }

    /**
     * 帖子点赞
     */
    @Override
    public RCode insertGossipPostLike(String post_id, String user_id) {
        int n = gossipPostMapper.insertGossipPostLike(post_id, user_id);
        int nn = gossipPostMapper.incrGossipPostLike(post_id);
        if(n > 0 && nn > 0){
            return RCode.pass("点赞成功");
        }
        return RCode.failure("点赞失败");
    }

    /**
     * 帖子取消点赞
     */
    @Override
    public RCode deleteGossipPostLike(String post_id, String user_id) {
        int n = gossipPostMapper.deleteGossipPostLike(post_id, user_id);
        int nn = gossipPostMapper.decrGossipPostLike(post_id);
        if(n > 0 && nn > 0){
            return RCode.pass("取消成功");
        }
        return RCode.failure("取消失败");
    }


    /**
     * 用户点赞查询
     */
    @Override
    public RCode selectGossipPostLike(String user_id) {
        List<String> pidList = gossipPostMapper.selectPostIdByUserLike(user_id);
        if( pidList != null ){
            return RCode.pass("查询成功", pidList);
        }
        return RCode.failure("用户未点赞");
    }

    /**
     * 帖子新增评论
     *
     * @param param
     */
    @Override
    public RCode addPostComment(PostCommentParam param) {
        Integer num = gossipPostMapper.insertPostComment(param);
        if( num > 0 ){
            return RCode.pass("评论成功");
        }
        return RCode.failure("评论失败");
    }


}
