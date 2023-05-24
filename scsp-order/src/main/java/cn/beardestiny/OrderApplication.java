package cn.beardestiny;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author BearDestiny
 * @Date 2023/5/1 6:45
 * @Sign “江湖夜雨十年灯”
 * @description: 订单服务启动类
 */
@MapperScan("cn.beardestiny.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run( OrderApplication.class, args);
    }
}
