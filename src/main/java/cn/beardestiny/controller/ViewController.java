package cn.beardestiny.controller;

import cn.beardestiny.feign.CarouselFeign;
import cn.beardestiny.feign.GoodFeign;
import cn.beardestiny.feign.GossipPostFeign;
import cn.beardestiny.feign.UserFeign;
import cn.beardestiny.param.*;
import cn.beardestiny.pojo.GossipPost;
import cn.beardestiny.pojo.User;
import cn.beardestiny.utils.ObjCastList;
import cn.beardestiny.utils.RCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author BearDestiny
 * @Date 2023/4/9 13:21
 * @Sign “江湖夜雨十年灯”
 * @description: 视图控制器
 */
@Controller
public class ViewController {

    @Resource
    private GossipPostFeign gossipPostFeign;

    @Resource
    private GoodFeign goodFeign;

    @Resource
    private UserFeign userFeign;

    @Resource
    private CarouselFeign carouselFeign;


    @GetMapping("/")
    @SuppressWarnings("unchecked")  //忽略类型转换警告
    public ModelAndView getIndex(){
        ModelAndView mv = new ModelAndView();
        System.out.println("====================");
        RCode resCode  = carouselFeign.getIndexCarousel();
        System.out.println(resCode);
        List<IndexCarousel> list = (ArrayList<IndexCarousel>)resCode.getData();
        mv.addObject("carouselList", list);
        System.out.println(list);
        mv.setViewName("index");
        return mv;
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/regSuccess")
    public String getRegSuccess(){
        return "regSuccess";
    }

    @GetMapping("/error_home")
    public String getError_home(){
        return "error_home";
    }

    @GetMapping("/home")
    public String getHome(){
        return "myinfo_base";
    }

    //校园墙视图，并返回数据
    @GetMapping("/gossipwall")
    @SuppressWarnings("unchecked")  //忽略类型转换警告
    public ModelAndView getGossipwall(ModelAndView mv){
        //服务调用，获取帖子
        List<GossipPostItem> list = (ArrayList<GossipPostItem>) gossipPostFeign.getGossipPostPage(1).getData();
        //布置视图
        mv.addObject("postList", list);
        mv.setViewName("gossipwall");
        return mv;
    }

    //校园墙分页视图
    @SuppressWarnings("unchecked")  //忽略类型转换警告
    @GetMapping("/gossipwall/page")
    @ResponseBody
    public ModelAndView getPageGossipwall(@RequestParam("pageNum") Integer pageNum, ModelAndView mv){
        System.out.println(pageNum);

        //服务调用
        List<GossipPostItem> list = (ArrayList<GossipPostItem>) gossipPostFeign.getGossipPostPage(pageNum).getData();
        System.out.println(list);
        //布置视图
        mv.addObject("postList", list);
        mv.setViewName("gossipPostItem");
        return mv;
    }

    //交易市场视图
    @GetMapping("/market")
    @SuppressWarnings("unchecked")  //忽略类型转换警告
    public ModelAndView getMarket(ModelAndView mv){
        RCode resCode = goodFeign.getMarketGoods(0L,0L,1,"order1", null, null);
        List<GoodItem> list = (ArrayList<GoodItem>) resCode.getData();
        //视图添加商品列表
        mv.addObject("goodList",list);

        RCode resCode2 = carouselFeign.getMarketCarousel();
        List<MarketCarousel> imgList = (ArrayList<MarketCarousel>) resCode2.getData();
        mv.addObject("carouselList",imgList);

        if(Objects.equals(resCode.getCode(), "001")){
            mv.setViewName("market");
            return mv;
        }
        mv.setViewName("error_home");
        return mv;
    }

    @GetMapping("/myinfo_deal")
    public ModelAndView getMyinfoDeal(ModelAndView mv){
        mv.setViewName("myinfo_deal");
        return mv;
    }


    @GetMapping("/askwall")
    public String getAskWall(){
        return "askwall";
    }


    @GetMapping("/stall")
    public String getStall(){
        return "stall";
    }


}
