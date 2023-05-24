package cn.beardestiny.service;

import cn.beardestiny.param.IndexCarousel;
import cn.beardestiny.utils.RCode;

import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/5/14 3:44
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
public interface CarouselService {

    RCode selectIndexCarousel();

    RCode selectMarketCarousel();
}
