package cn.beardestiny.controller;

import cn.beardestiny.feign.UserFeign;
import cn.beardestiny.param.TalkParam;
import cn.beardestiny.utils.RCode;
import org.bouncycastle.math.raw.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/5/4 17:29
 * @Sign “江湖夜雨十年灯”
 * @description: 用户视图控制器
 */
@Controller
public class UserController {

    @Resource
    private UserFeign userFeign;


    @GetMapping("/user/front/{uid}")
    @ResponseBody
    public ModelAndView getMyInfoHead(@PathVariable("uid") String user_id){
        RCode resCode = userFeign.getFrontUser(user_id);
        System.out.println(resCode.getData());
        HashMap<?,?> map = (LinkedHashMap<?,?>) resCode.getData();
        ModelAndView mv = new ModelAndView();
        mv.addObject("frontUser", map);
        mv.setViewName("myinfoHead");
        return mv;
    }

    @GetMapping("/user/talk")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public ModelAndView getTalk(@RequestParam("listener_id") String listener_id){
        RCode resCode = userFeign.getUserTalk(listener_id);
        List<TalkParam> list = (ArrayList<TalkParam>) resCode.getData();
        ModelAndView mv = new ModelAndView();
        mv.addObject("talkList", list);
        mv.setViewName("talkToast");
        return mv;
    }
}
