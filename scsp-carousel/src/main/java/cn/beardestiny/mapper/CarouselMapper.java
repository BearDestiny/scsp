package cn.beardestiny.mapper;

import cn.beardestiny.param.IndexCarousel;
import cn.beardestiny.param.MarketCarousel;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/5/14 3:43
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@Repository
public interface CarouselMapper {


    @Select("select u.user_nickname as sender_name, vip.reciver_name, vip.post_theme as theme, vip.post_img as carousel_img\n" +
            "from gossip_vip_post vip\n" +
            "join user u on vip.sender_id = u.user_id")
    List<IndexCarousel> getIndexCarousel();


    @Select("select g.cover_img, c.good_id \n" +
            "from `good_carousel` c\n" +
            "join good g on c.good_id = g.good_id \n" +
            "where g.good_number != 0")
    List<MarketCarousel> getMarketCarousel();
}
