package cn.beardestiny.controller;

import cn.beardestiny.param.BuyParam;
import cn.beardestiny.pojo.GoodOrder;
import cn.beardestiny.service.OrderService;
import cn.beardestiny.utils.RCode;
import cn.beardestiny.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author BearDestiny
 * @Date 2023/5/1 6:57
 * @Sign “江湖夜雨十年灯”
 * @description: 订单业务控制器
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;


    /**
     * 订单保存
     */
    @PostMapping("/save")
    public RCode saveOrder(@RequestBody BuyParam buyParam) {

        SnowflakeIdWorker snow = new SnowflakeIdWorker(4);
        GoodOrder order = new GoodOrder();
        order.setOrder_id("oid" + snow.nextId());
        order.setGood_id(buyParam.getGood_id());
        order.setBuy_num(buyParam.getBuy_num());
        order.setOrder_status(0);
        order.setBuyer_id(buyParam.getBuyer_id());

        return orderService.saveOrder(order);
    }


    /**
     * 订单查询
     */
    @GetMapping("/get")
    public RCode getMyOrder(@RequestParam("uid") String user_id){
        return orderService.getMyOrder(user_id);
    }

}
