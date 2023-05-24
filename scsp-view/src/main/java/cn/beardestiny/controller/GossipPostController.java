package cn.beardestiny.controller;

import cn.beardestiny.feign.GossipPostFeign;
import cn.beardestiny.param.GossipPostItem;
import cn.beardestiny.utils.RCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/5/14 0:22
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@Controller
public class GossipPostController {

    @Resource
    private GossipPostFeign gossipPostFeign;

    @GetMapping("/gossipwall/searchLike")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public ModelAndView getGossipByLike(@RequestParam("keyword") String keyword){
        ModelAndView mv = new ModelAndView();
        RCode resCode = gossipPostFeign.getGossipPostsByLikeWord(keyword);
        List<GossipPostItem> list = (ArrayList<GossipPostItem>) resCode.getData();
        mv.addObject("postList", list);
        mv.setViewName("gossipPostItem");
        return mv;
    }
}
