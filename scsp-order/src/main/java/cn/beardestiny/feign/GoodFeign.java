package cn.beardestiny.feign;

import cn.beardestiny.utils.RCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author BearDestiny
 * @Date 2023/5/5 18:44
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@FeignClient("scsp-market")
public interface GoodFeign {

    @GetMapping("/market/good/{gid}")
    RCode getGoodById(@PathVariable("gid") String gid);
}
