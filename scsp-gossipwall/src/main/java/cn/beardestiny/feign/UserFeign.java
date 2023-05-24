package cn.beardestiny.feign;

import cn.beardestiny.utils.RCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author BearDestiny
 * @Date 2023/4/16 16:09
 * @Sign “江湖夜雨十年灯”
 * @description: 用户服务接口
 */
@FeignClient("scsp-user")
public interface UserFeign {

    @GetMapping("/user/verifiedUserToken")
    RCode verifiedUserToken(@RequestHeader(value = "userToken", required = false) String userToken);

    @GetMapping("/user/{user_account}")
    RCode getUser(@PathVariable("user_account") String user_account);
}
