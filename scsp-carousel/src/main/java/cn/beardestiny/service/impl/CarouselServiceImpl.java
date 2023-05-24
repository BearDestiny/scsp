package cn.beardestiny.service.impl;

import cn.beardestiny.mapper.CarouselMapper;
import cn.beardestiny.param.IndexCarousel;
import cn.beardestiny.param.MarketCarousel;
import cn.beardestiny.service.CarouselService;
import cn.beardestiny.utils.RCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/5/14 3:45
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    @Override
    public RCode selectIndexCarousel() {
        List<IndexCarousel> list =  carouselMapper.getIndexCarousel();
        if( list.size() > 0 ){
            return RCode.pass("查询成功",list);
        }
        return RCode.failure("查询完毕");
    }

    @Override
    public RCode selectMarketCarousel() {
        List<MarketCarousel> list = carouselMapper.getMarketCarousel();
        if( list.size() > 0 ){
            return RCode.pass("查询成功",list);
        }
        return RCode.failure("查询完毕");
    }
}
