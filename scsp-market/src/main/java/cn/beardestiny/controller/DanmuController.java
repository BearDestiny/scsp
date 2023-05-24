package cn.beardestiny.controller;

import cn.beardestiny.param.DanmuParam;
import cn.beardestiny.service.DanmuService;
import cn.beardestiny.utils.RCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author BearDestiny
 * @Date 2023/5/3 19:15
 * @Sign “江湖夜雨十年灯”
 * @description: 弹幕控制器
 */
@RestController
public class DanmuController {

    @Resource
    private DanmuService danmuService;

    @PostMapping("/market/addDanmu")
    public RCode addDamnu(@RequestBody DanmuParam danmuParam){
        return danmuService.insertDanmu(danmuParam);
    }

    @GetMapping("/market/getDanmu")
    public RCode getDanmu(){
        return danmuService.selectDanmuList();
    }
}
