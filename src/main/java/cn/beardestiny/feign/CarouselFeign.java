package cn.beardestiny.feign;

import cn.beardestiny.utils.RCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author BearDestiny
 * @Date 2023/5/14 4:00
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@FeignClient("scsp-carousel")
public interface CarouselFeign {

    @GetMapping("/carousel/index")
    RCode getIndexCarousel();

    @GetMapping("/carousel/market")
    public RCode getMarketCarousel();
}
