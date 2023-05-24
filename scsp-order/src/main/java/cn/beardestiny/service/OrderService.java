package cn.beardestiny.service;

import cn.beardestiny.pojo.GoodOrder;
import cn.beardestiny.utils.RCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author BearDestiny
 * @Date 2023/5/1 6:59
 * @Sign “江湖夜雨十年灯”
 * @description: 订单服务接口
 */
public interface OrderService extends IService<GoodOrder> {


    RCode saveOrder(GoodOrder order);

    RCode getMyOrder(String user_id);

}
