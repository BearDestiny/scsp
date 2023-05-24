package cn.beardestiny.controller;

import cn.beardestiny.param.GoodSinglePage;
import cn.beardestiny.param.NewGoodParam;
import cn.beardestiny.pojo.Good;
import cn.beardestiny.service.GoodService;
import cn.beardestiny.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Author BearDestiny
 * @Date 2023/4/22 3:44
 * @Sign “江湖夜雨十年灯”
 * @description: 商品业务控制器
 */

@Slf4j
@RestController
@RequestMapping("/market")
public class GoodController {

    @Resource
    private GoodService goodService;

    @Resource
    private FeignGetUid uidFeign;

    @Resource
    private AliyunOSSUtil aliyunOSSUtil;


    /**
     * 上传商品
     */
    @PostMapping("/addGood")
    public RCode uploadGood(@Validated NewGoodParam newGoodParam, BindingResult result,
                            @RequestParam("coverimg") MultipartFile cover_img,
                            @RequestParam("imgList") MultipartFile[] good_imgList,
                            @RequestHeader(value = "userToken", required = false) String token) throws Exception {

        boolean bool = result.hasErrors();

        //空值校验
        if ( bool || cover_img == null || good_imgList == null){
            return RCode.failure("参数含空");
        }

        //预制上传图片相关变量
        String fileName = null;         //uuid格式图片名
        String contentType = null;      //图片格式
        String url = null;          //图片url
        List<String> imgList = new ArrayList<>();            //图片sql values，方便后续插入mysql


        //feign调用scsp-user，验证用户当前token状态，检查是否过期，返回uid
        String seller_id = uidFeign.getUid(token);

        //雪花算法生成 商品id ：gid
        SnowflakeIdWorker snow = new SnowflakeIdWorker(2);

        //实体类属性赋值
        Good good = new Good();
        good.setSeller_id(seller_id);
        good.setGood_id("gid" + snow.nextId());
        good.setGood_name(newGoodParam.getGood_name());
        good.setCategory_id(newGoodParam.getCategory_id());
            //封面图片名
        fileName = "img/"+good.getGood_id()+"/" + UUID.randomUUID().toString().replace("-","").toLowerCase();
        good.setCover_img(fileName);
            //小数位数控制
        Double price = Double.parseDouble(newGoodParam.getGood_price());
        DecimalFormat df = new DecimalFormat("#.00");
        good.setGood_price( Double.valueOf(df.format(price) ) );
        good.setGood_number( Integer.parseInt( newGoodParam.getGood_num() )  );
        good.setGood_detail(newGoodParam.getGood_detail() );

        //mysql持久化 商品
        RCode addCode = goodService.addGood(good);
        RCode goodImgCode = null;
        //OSS存储图片
        if (Objects.equals(addCode.getCode(), "001")){
            //封面
            contentType = cover_img.getContentType();
            url = aliyunOSSUtil.uploadImage(fileName, cover_img.getBytes(), contentType);
            log.info("图片上传成功，地址为："+ url);

            //商品详情多图
            for( MultipartFile img : good_imgList ){
                fileName = "img/"+good.getGood_id()+"/" + UUID.randomUUID().toString().replace("-","").toLowerCase();
                contentType = img.getContentType();
                url = aliyunOSSUtil.uploadImage(fileName, img.getBytes(), contentType);
                //mysql存储图片的参数list，格式控制 (post_id, img_id)
                imgList.add("(\'"+good.getGood_id()+"\',\'"+fileName+"\')");
                log.info("图片上传成功，地址为："+ url);
            }
            //mysql持久化 商品图片
            goodImgCode = goodService.addGoodImg(imgList);
        }

        if (Objects.equals(addCode.getCode(), "001") && Objects.equals(goodImgCode.getCode(), "001")){
            return RCode.pass("添加成功");
        }
        return RCode.failure("添加失败");
    }


    /**
     * 按序查询15个商品
     */
    @GetMapping("/getMarketGoods")
    public RCode getMarketGoods(@RequestParam(value = "leftPrice" , required = false) Long leftPrice,
                                                            @RequestParam(value = "rightPrice", required = false) Long rightPrice,
                                                            @RequestParam("pageNum") int pageNum,
                                                            @RequestParam("orderWay") String orderWay,
                                                            @RequestParam(value = "likeName", required = false) String likeName,
                                                            @RequestParam(value= "category", required = false) String category){
        if(leftPrice == null){
            leftPrice = 0L;
        }
        if(rightPrice == null){
            rightPrice = 0L;
        }
        if( likeName != null ){
            likeName = "%"+likeName+"%";
        }
        System.out.println("====================================================");
        System.out.println(leftPrice);
        System.out.println(rightPrice);
        System.out.println(pageNum);
        System.out.println(orderWay);
        System.out.println(likeName);
        System.out.println(category);
        System.out.println("====================================================");

        return goodService.getPageGood(leftPrice,rightPrice,pageNum,orderWay,likeName, category);
    }


    /**
     * 查询单个商品
     */
    @GetMapping("/good/{gid}")
    public RCode getGoodById(@PathVariable("gid") String gid){
        GoodSinglePage good = goodService.getGoodById(gid);
        if( good != null ){
            return RCode.pass("查询成功", good);
        }
        return RCode.failure("查询失败");
    }

    /**
     * 收藏商品
     */
    @GetMapping("/collect")
    public RCode collectGood(@RequestParam("good_id") String gid, @RequestParam("user_id") String uid){
        RCode resCode = goodService.getGoodCollect(gid, uid);
        //已收藏
        if(Objects.equals(resCode.getCode(), "001")){
            return RCode.failure("已收藏");
        }
        return  goodService.addGoodCollect(gid, uid);
    }


    /**
     * 卖家全部商品
     */
    @GetMapping("/stall/{nickname}/{orderWay}")
    public RCode stallGood(@PathVariable("nickname") String user_nickname, @PathVariable("orderWay") String orderWay){
        RCode resCode = goodService.getStall(user_nickname, orderWay);
        return resCode;
    }


    /**
     * 我的商品
     */
    @GetMapping("/mygood")
    public RCode myGood(@RequestParam("uid") String user_id){
        return goodService.getMyGood(user_id);
    }

}
