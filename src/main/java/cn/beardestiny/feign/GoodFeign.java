package cn.beardestiny.feign;

import cn.beardestiny.utils.RCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author BearDestiny
 * @Date 2023/4/28 15:25
 * @Sign “江湖夜雨十年灯”
 * @description: 商品服务接口
 */
@FeignClient("scsp-market")
public interface GoodFeign {

    @GetMapping("/market/getMarketGoods")
    public RCode getMarketGoods(@RequestParam(value = "leftPrice", required = false) Long leftPrice,
                                @RequestParam(value = "rightPrice", required = false) Long rightPrice,
                                @RequestParam("pageNum") int pageNum,
                                @RequestParam("orderWay") String orderWay,
                                @RequestParam(value = "likeName", required = false) String likeName,
                                @RequestParam(value = "category", required = false) String category);

    @GetMapping("/market/good/{gid}")
    public RCode getGoodById(@PathVariable("gid") String gid);

    @GetMapping("/market/stall/{nickname}/{orderWay}")
    public RCode stallGood(@PathVariable("nickname") String user_nickname, @PathVariable("orderWay") String orderWay);

    @GetMapping("/market/mygood")
    public RCode myGood(@RequestParam("uid") String user_id);
}
