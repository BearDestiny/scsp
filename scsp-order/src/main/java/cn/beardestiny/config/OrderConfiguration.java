package cn.beardestiny.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author BearDestiny
 * @Date 2023/5/1 6:47
 * @Sign “江湖夜雨十年灯”
 * @description: 订单配置类
 */
@Configuration
public class OrderConfiguration {
    /**
     * mq序列化方式为 json
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
