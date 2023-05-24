package cn.beardestiny.controller;

import cn.beardestiny.param.UpdateUserInfoParam;
import cn.beardestiny.service.UserService;
import cn.beardestiny.utils.RCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author BearDestiny
 * @Date 2023/4/16 19:27
 * @Sign “江湖夜雨十年灯”
 * @description: 用户类通用请求控制器
 */
@RestController
@RequestMapping("/user")
public class UserGeneralController {

    @Resource
    private UserService userService;

    @GetMapping("/{user_id}")
    public RCode getUser(@PathVariable("user_id") String user_id){
        return userService.selectUserById(user_id);
    }

    @GetMapping("/front/{user_id}")
    public RCode getFrontUser(@PathVariable("user_id") String user_id){
        return userService.selectFrontUser(user_id);
    }

    @GetMapping("/nickname/{user_nickname}")
    public RCode getUserByNickname(@PathVariable("user_nickname") String nickname){
        return userService.selectUserByNickname(nickname);
    }

    @GetMapping("/getUserInfo/{uid}")
    public RCode getUserInfo(@PathVariable("uid") String uid){
        return userService.selectUserInfoById(uid);
    }

    @PostMapping("/updateUserInfo")
    public RCode updateUserInfo(@RequestBody UpdateUserInfoParam infoParam){
        return userService.updateUserInfo(infoParam);
    }

    /**
     * 查询用户留言
     */
    @GetMapping("/getTalk")
    public RCode getUserTalk(@RequestParam("listener_id") String listener_id){
        //根据id查询用户所有未读评论
        return userService.getUserTalk(listener_id);
    }

    /**
     * 已读留言
     */
    @GetMapping("/readTalk")
    public RCode updateTalkStatus(@RequestParam("tid") Long tid){
        System.out.println("================拿到了");
        System.out.println(tid);
        return userService.updateTalk( tid );
    }
}
