package cn.beardestiny.service.Impl;

import cn.beardestiny.mapper.GoodMapper;
import cn.beardestiny.param.GoodItem;
import cn.beardestiny.param.GoodSinglePage;
import cn.beardestiny.param.MyGood;
import cn.beardestiny.pojo.Good;
import cn.beardestiny.service.GoodService;
import cn.beardestiny.utils.RCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/4/22 3:42
 * @Sign “江湖夜雨十年灯”
 * @description: 商品业务实现类
 */
@Service
public class GoodServiceImpl implements GoodService {

    @Resource
    private GoodMapper goodMapper;


    /**
     * 新增商品
     */
    @Override
    public RCode addGood(Good good) {
        int num = goodMapper.insert(good);

        if( num > 0 ){
            return RCode.pass("添加成功");
        }
        return RCode.failure("添加失败");
    }

    /**
     * 添加商品详情图片
     */
    @Override
    public RCode addGoodImg(List<String> imgList) {
        String sql = imgList.toString().replaceAll("^\\[|\\]$","");
        int num = goodMapper.insertGoodImg(sql);
        if( num > 0  ){
            return RCode.pass("插入成功");
        }
        return RCode.failure("插入失败");
    }

    /**
     * 按序查询15个商品
      * @Param theway，查询方式，1是新上架；2是价格升序；3是价格降序
     */
    @Override
    public RCode getPageGood(long leftPrice, long rightPrice, int pageNum, String orderWay, String likeName, String category) {
        pageNum =  (pageNum - 1) * 15;
        List<GoodItem> goodItemList = goodMapper.selectPageGood(leftPrice, rightPrice, pageNum, orderWay, likeName, category);
        if (goodItemList != null){
            return RCode.pass("查询成功", goodItemList);
        }
        return RCode.failure("查询失败");
    }

    /**
     * 根据gid查询单个商品
     *
     * @param gid
     */
    @Override
    public GoodSinglePage getGoodById(String gid) {
        GoodSinglePage good = goodMapper.selectGoodById(gid);
        if ( good != null ){
            return good;
        }
        return null;
    }

    /**
     * 根据gid更新商品状态
     *
     * @param gid
     */
    @Override
    public RCode updateGoodStatus(String gid) {
        int i = goodMapper.updateGoodStatus(gid);
        if( i > 0 ){
            return RCode.pass("修改成功");
        }
        return RCode.failure("修改失败");
    }

    /**
     * 添加商品收藏记录
     *
     * @param gid
     * @param uid
     */
    @Override
    public RCode addGoodCollect(String gid, String uid) {
        int num = goodMapper.insertGoodCollect(gid, uid);
        if( num > 0 ){
            return RCode.pass("收藏成功");
        }
        return RCode.failure("收藏失败");
    }

    /**
     * 获取用户商品收藏记录
     *
     * @param gid
     * @param uid
     */
    @Override
    public RCode getGoodCollect(String gid, String uid) {
        Date date = goodMapper.selectGoodCollect(gid, uid);
        if( date != null ){
            return RCode.pass("已收藏");
        }
        return RCode.failure("未收藏");
    }

    /**
     * 获取买家所有商品
     *
     * @param user_nickname
     * @param orderWay
     */
    @Override
    public RCode getStall(String user_nickname, String orderWay) {
        List<GoodItem> list = goodMapper.selectStall(user_nickname, orderWay);

        if ( list != null ){
            return RCode.pass("查询成功", list);
        }
        return RCode.failure("查询失败");
    }

    /**
     * 商品库存更新
     */
    @Override
    public RCode updateGoodNum(String gid, int buy_num) {
        int num = goodMapper.updateGoodNum(gid, buy_num);
        if( num > 0 ){
            return RCode.pass("更新成功");
        }
        return RCode.failure("更新失败");
    }

    /**
     * 根据id获取用户上架全部商品
     */
    @Override
    public RCode getMyGood(String user_id) {
        List<MyGood> list = goodMapper.selectMyGood(user_id);
        if( list.size() > 0 ){
            return RCode.pass("查询成功", list);
        }
        return RCode.failure("查询完毕");
    }
}
