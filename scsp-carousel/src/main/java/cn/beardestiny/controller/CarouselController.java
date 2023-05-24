package cn.beardestiny.controller;

import cn.beardestiny.service.CarouselService;
import cn.beardestiny.utils.RCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author BearDestiny
 * @Date 2023/5/14 3:30
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@RestController
public class CarouselController {

    @Resource
    private CarouselService carouselService;

    @GetMapping("/carousel/index")
    public RCode getIndexCarousel(){
        return  carouselService.selectIndexCarousel();
    }

    @GetMapping("/carousel/market")
    public RCode getMarketCarousel(){
        return carouselService.selectMarketCarousel();
    }
}
