package cn.beardestiny.listen;

import cn.beardestiny.mapper.UserMapper;
import cn.beardestiny.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author BearDestiny
 * @Date 2023/5/14 10:39
 * @Sign “江湖夜雨十年灯”
 * @description: 帖子业务监听器
 */
@Slf4j
@Component
public class GossipwallRabbitMqListener {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    /**
     * 监听普通发帖
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "update.queue"),
                    exchange = @Exchange(value = "topic.ex"),
                    key = "addPost.gossip"
            )
    )
    public void updateUserCurrency(String user_id){
        System.out.println("拿到了==========="+user_id);
        //用户货币减5
        Long postPrice = 5L;
        userMapper.updateUserCurrency( postPrice, user_id );
    }
}
