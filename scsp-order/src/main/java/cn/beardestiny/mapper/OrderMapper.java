package cn.beardestiny.mapper;

import cn.beardestiny.param.MyOrder;
import cn.beardestiny.pojo.GoodOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/5/1 7:00
 * @Sign “江湖夜雨十年灯”
 * @description: 订单持久层接口
 */
@Repository
public interface OrderMapper extends BaseMapper<GoodOrder> {

    @Select("select o.order_id, g.good_name, g.cover_img, o.buy_num, o.order_status, u.user_nickname as seller_nickname, o.create_time, g.good_price\n" +
            "from good_order o\n" +
            "left join good g on g.good_id = o.good_id\n" +
            "left join user u on g.seller_id = u.user_id\n" +
            "where o.buyer_id = #{uid}")
    List<MyOrder> selectMyOrder(@Param("uid") String uid);
}
