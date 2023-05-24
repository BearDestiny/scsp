package cn.beardestiny;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author BearDestiny
 * @Date 2023/4/16 15:46
 * @Sign “江湖夜雨十年灯”
 * @description: 青鸟传信服务启动类
 */
@MapperScan("cn.beardestiny.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling   //允许开启定时任务
@SpringBootApplication
public class GossipwallApplication {
    public static void main(String[] args) {
        SpringApplication.run(GossipwallApplication.class, args);
    }
}
