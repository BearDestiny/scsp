package cn.beardestiny.mapper;

import cn.beardestiny.param.GoodItem;
import cn.beardestiny.param.GoodSinglePage;
import cn.beardestiny.param.MyGood;
import cn.beardestiny.pojo.Good;
import cn.beardestiny.utils.RCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/4/22 3:38
 * @Sign “江湖夜雨十年灯”
 * @description: 商品类mapper
 */

@Repository
public interface GoodMapper extends BaseMapper<Good> {

    /**
     * 添加商品详情图片
     */
    @Insert("insert into `good_img`(good_id, detail_img) values${imgs}")
    int insertGoodImg(@Param("imgs") String imgs);

    /**
     * 分页查询15个商品
     */
    List<GoodItem> selectPageGood(@Param("leftPrice") long leftPrice,
                                  @Param("rightPrice") long rightPrice,
                                  @Param("pageNum") int pageNum,
                                  @Param("orderWay") String orderWay,
                                  @Param("likeName") String likeName,
                                  @Param("category") String category);

    /**
     * id查询单个商品
     */
    GoodSinglePage selectGoodById(@Param("gid") String gid);

    /**
     * id更新商品状态
     */
    @Update("update `good` set good_status = 1 where good_id = #{gid}")
    int updateGoodStatus(@Param("gid") String gid);

    /**
     * 收藏商品
     */
    @Insert("insert into  `good_collect`(good_id, user_id) values(#{gid}, #{uid})")
    int insertGoodCollect(@Param("gid") String gid, @Param("uid") String uid);

    /**
     * 查询收藏状态
     */
    @Select("select create_time from `good_collect` where good_id = #{gid} and user_id = #{uid}")
    Date selectGoodCollect(@Param("gid") String gid, @Param("uid") String uid);

    /**
     * 用户昵称查询所有商品
     */
    List<GoodItem> selectStall(@Param("nickname") String user_nickname, @Param("orderWay") String orderWay);

    /**
     * 更新商品库存
     */
    int updateGoodNum(@Param("gid") String gid, @Param("buyNum") int buy_num);

    /**
     * 用户id查询所有个人商品
     */
    List<MyGood> selectMyGood(@Param("uid") String user_id);

}

