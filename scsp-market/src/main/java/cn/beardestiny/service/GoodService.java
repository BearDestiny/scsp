package cn.beardestiny.service;

import cn.beardestiny.param.GoodItem;
import cn.beardestiny.param.GoodSinglePage;
import cn.beardestiny.pojo.Good;
import cn.beardestiny.utils.RCode;

import java.sql.Date;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/4/22 3:41
 * @Sign “江湖夜雨十年灯”
 * @description: 商品业务类
 */

public interface GoodService {

    /**
     * 新增商品
     */
    RCode addGood(Good good);


    /**
     * 添加商品详情图片
     */
    RCode addGoodImg(List<String> imgs);


    /**
     * 随机查询15个商品
     * @Param theway，查询方式，1是新上架；2是价格升序；3是价格降序
     */
    RCode getPageGood(long leftPrice, long rightPrice, int pageNum, String orderWay, String likeName, String category);


    /**
     * 根据gid查询单个商品
     */
    GoodSinglePage getGoodById(String gid);


    /**
     * 根据gid更新商品状态
     */
    RCode updateGoodStatus(String gid);


    /**
     * 添加商品收藏记录
     */
    RCode addGoodCollect(String gid, String uid);


    /**
     * 获取用户商品收藏记录
     */
    RCode getGoodCollect(String gid, String uid);


    /**
     * 获取买家所有商品
     */
    RCode getStall(String user_nickname, String orderWay);

    /**
     * 商品库存更新
     */
    RCode updateGoodNum(String gid, int buy_num);

    /**
     * 根据id获取用户上架全部商品
     */
    RCode getMyGood(String user_id);

}
