package cn.beardestiny.feign;

import cn.beardestiny.utils.RCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author BearDestiny
 * @Date 2023/5/13 22:51
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@FeignClient("scsp-order")
public interface OrderFeign {

    @GetMapping("/order/get")
    public RCode getMyOrder(@RequestParam("uid") String user_id);
}
