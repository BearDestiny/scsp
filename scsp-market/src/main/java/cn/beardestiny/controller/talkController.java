package cn.beardestiny.controller;

import cn.beardestiny.param.FrontTalk;
import cn.beardestiny.service.TalkService;
import cn.beardestiny.utils.RCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author BearDestiny
 * @Date 2023/5/2 4:16
 * @Sign “江湖夜雨十年灯”
 * @description: 商品交流控制器
 */
@RestController
@RequestMapping("/market")
public class talkController {

    @Resource
    private TalkService talkService;

    @PostMapping("/talk")
    public RCode talkTo(@RequestBody FrontTalk talk){
        return talkService.addTalkTo(talk);
    }
}
