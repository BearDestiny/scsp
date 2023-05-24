package cn.beardestiny.feign;

import cn.beardestiny.utils.RCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author BearDestiny
 * @Date 2023/4/21 0:11
 * @Sign “江湖夜雨十年灯”
 * @description: 用户服务接口
 */

@FeignClient("scsp-user")
public interface UserFeign {


    @GetMapping("/user/{user_id}")
    public RCode getUser(@PathVariable("user_id") String user_id);

    @GetMapping("/user/front/{user_id}")
    public RCode getFrontUser(@PathVariable("user_id") String user_id);

    @GetMapping("/user/nickname/{user_nickname}")
    public RCode getUserByNickname(@PathVariable("user_nickname") String nickname);

    @GetMapping("/verifiedUserToken")
    public RCode verifiedUserToken(@RequestHeader(value = "userToken", required = false) String userToken);

    @GetMapping("/user/getTalk")
    public RCode getUserTalk(@RequestParam("listener_id") String listener_id);

}
