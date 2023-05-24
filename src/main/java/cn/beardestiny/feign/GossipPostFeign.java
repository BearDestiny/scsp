package cn.beardestiny.feign;

import cn.beardestiny.utils.RCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author BearDestiny
 * @Date 2023/4/17 16:18
 * @Sign “江湖夜雨十年灯”
 * @description: 校园墙帖子接口
 */
@FeignClient("scsp-gossipwall")
public interface GossipPostFeign {

    @GetMapping("/gossipwall/getGossipPostPage")
    RCode getGossipPostPage(@RequestParam("pageNum") int pageNum);

    @GetMapping("/gossipwall/getGossipPostLike")
    public RCode getGossipPostsByLikeWord(@RequestParam("keyword") String keyword);

//    @GetMapping("/gossipwall/userGossipPostLikeList")
//    RCode getUserGossipPostLikeList(@RequestParam("user_id") String user_id);
}
