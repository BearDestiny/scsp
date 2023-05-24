package cn.beardestiny.listen;

import cn.beardestiny.service.GoodService;
import cn.beardestiny.utils.RCode;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/5/1 8:39
 * @Sign “江湖夜雨十年灯”
 * @description: 交易业务监听器
 */
@Component
public class MarketRabbitMqListener {

    @Autowired
    private GoodService goodService;


    /**
     * 监听商品数量
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "update.queue"),
                    exchange = @Exchange(value = "topic.ex"),
                    key = "buyNum.good"
            )
    )
    public void updateGoodNum(List<String> infoList){
        System.out.println("buyNum获取到了请求===============================");
        //库存够，减数量
        if( Integer.parseInt(infoList.get(2)) > Integer.parseInt(infoList.get(1)) ) {
            goodService.updateGoodNum(infoList.get(0), Integer.parseInt(infoList.get(1)));
        }
        //库存清空，更改商品状态
        if( Integer.parseInt(infoList.get(2)) == Integer.parseInt(infoList.get(1)) ){
            goodService.updateGoodNum(infoList.get(0), Integer.parseInt(infoList.get(1)));
            goodService.updateGoodStatus(infoList.get(0));
        }
    }

}
