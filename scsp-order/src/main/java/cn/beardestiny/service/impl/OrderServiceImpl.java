package cn.beardestiny.service.impl;

import cn.beardestiny.feign.GoodFeign;
import cn.beardestiny.mapper.OrderMapper;
import cn.beardestiny.param.MyOrder;
import cn.beardestiny.pojo.GoodOrder;
import cn.beardestiny.service.OrderService;
import cn.beardestiny.utils.RCode;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/5/1 7:02
 * @Sign “江湖夜雨十年灯”
 * @description: 订单业务实现类
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, GoodOrder> implements OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private GoodFeign goodFeign;

    @Resource
    private OrderMapper orderMapper;

    @Override
    public RCode saveOrder(GoodOrder order) {
        //查询库存数量
        RCode resCode = goodFeign.getGoodById(order.getGood_id());
        HashMap<?,?> map = (LinkedHashMap<?,?>) resCode.getData();
        Integer leftNum = (Integer) map.get("good_number");
        if( order.getBuy_num() >leftNum ){
            return RCode.failure("数量异常");
        }

        boolean bool = save(order);
        if( bool ){
            //发送商品服务消息（数量同步）
//            rabbitTemplate.convertAndSend("topic.ex","status.good",order.getGood_id());

            List<String> goodInfoLIst = new ArrayList<>();
            goodInfoLIst.add(order.getGood_id());
            goodInfoLIst.add(String.valueOf(order.getBuy_num()));
            goodInfoLIst.add(String.valueOf(leftNum));
            rabbitTemplate.convertAndSend("topic.ex","buyNum.good",goodInfoLIst);
            //发送轮播图服务消息

            return RCode.pass("保存成功");
        }
        return RCode.failure("保存失败");
    }

    @Override
    public RCode getMyOrder(String user_id) {
        List<MyOrder> list = orderMapper.selectMyOrder(user_id);
        if( list.size() > 0 ){
            return RCode.pass("查询成功", list);
        }
        return RCode.failure("查询完毕");
    }
}
