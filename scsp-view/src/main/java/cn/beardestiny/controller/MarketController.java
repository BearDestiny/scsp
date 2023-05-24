package cn.beardestiny.controller;

import cn.beardestiny.feign.GoodFeign;
import cn.beardestiny.feign.OrderFeign;
import cn.beardestiny.feign.UserFeign;
import cn.beardestiny.param.GoodItem;
import cn.beardestiny.param.GoodSinglePage;
import cn.beardestiny.param.MyGood;
import cn.beardestiny.param.MyOrder;
import cn.beardestiny.pojo.GoodOrder;
import cn.beardestiny.pojo.User;
import cn.beardestiny.utils.RCode;
import com.alibaba.nacos.shaded.io.perfmark.Link;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author BearDestiny
 * @Date 2023/4/28 17:15
 * @Sign “江湖夜雨十年灯”
 * @description: 商品数据视图
 */
@Controller
public class MarketController {

    @Resource
    private GoodFeign goodFeign;

    @Resource
    private UserFeign userFeign;

    @Resource
    private OrderFeign orderFeign;

    @GetMapping("/market/getOrderPage")
    @SuppressWarnings("unchecked")
    public ModelAndView getOrderPage(@RequestParam(value = "leftPrice", required = false) Long leftPrice,
                                     @RequestParam(value = "rightPrice", required = false) Long rightPrice,
                                     @RequestParam("pageNum") int pageNum,
                                     @RequestParam("orderWay") String orderWay,
                                     @RequestParam(value = "likeName", required = false) String likeName,
                                     @RequestParam(value = "category", required = false) String category){
        if(leftPrice == null){
            leftPrice = 0L;
        }
        if(rightPrice == null){
            rightPrice = 0L;
        }

        RCode resCode = goodFeign.getMarketGoods(leftPrice, rightPrice, pageNum, orderWay, likeName, category);
        List<GoodItem> goodList = (ArrayList<GoodItem>) resCode.getData();

        ModelAndView mv = new ModelAndView();
        mv.addObject("goodList", goodList);
        mv.setViewName("marketGoodItem");
        return mv;
    }

    @GetMapping("/good/{gid}")
    public ModelAndView getGoodView(@PathVariable("gid") String gid){
        ModelAndView mv = new ModelAndView();
        RCode resCode = goodFeign.getGoodById(gid);
        if(Objects.equals(resCode.getCode(), "001")){
            HashMap<?,?> map = (LinkedHashMap<?,?>) resCode.getData();
            mv.addObject("good", map);
            mv.setViewName("goods");
            return mv;
        }
        mv.setViewName("error_home");
        return mv;
    }

    @GetMapping("/stall/{nickname}/{orderWay}")
    @SuppressWarnings("unchecked")
    public ModelAndView getStall(@PathVariable("nickname") String nickname , @PathVariable("orderWay") String orderWay){
        ModelAndView mv = new ModelAndView();

        RCode resCode = goodFeign.stallGood(nickname, orderWay);
        RCode userCode = userFeign.getUserByNickname(nickname);
        List<GoodItem> goodList = (ArrayList<GoodItem>) resCode.getData();
        HashMap<?,?> map = (LinkedHashMap<?,?>) userCode.getData();
        mv.addObject("goodList", goodList);
        mv.addObject("nickname", nickname);
        mv.addObject("seller", map);
        mv.setViewName("stall");
        return mv;
    }


    @GetMapping("/market/mygood")
    @ResponseBody
    @SuppressWarnings("unchecked")  //忽略类型转换警告
    public ModelAndView getMyGood(@RequestParam("uid") String uid){
        ModelAndView mv = new ModelAndView();
        RCode resCode = goodFeign.myGood(uid);
        List<MyGood> list = (ArrayList<MyGood>) resCode.getData();
        //视图添加商品列表
        mv.addObject("goodList", list);
        if(Objects.equals(resCode.getCode(), "001")){
            mv.setViewName("myGoodItem");
            return mv;
        }
        mv.setViewName("error_home");
        return mv;
    }


    @GetMapping("/order/get")
    @ResponseBody
    @SuppressWarnings("unchecked")  //忽略类型转换警告
    public ModelAndView getMyOrder(@RequestParam("uid") String uid){
        ModelAndView mv = new ModelAndView();
        RCode resCode = orderFeign.getMyOrder(uid);
        List<MyOrder> list = (ArrayList<MyOrder>) resCode.getData();
        //视图添加商品列表
        mv.addObject("orderList", list);
        if(Objects.equals(resCode.getCode(), "001")){
            mv.setViewName("myOrderItem");
            return mv;
        }
        mv.setViewName("error_home");
        return mv;
    }

}
