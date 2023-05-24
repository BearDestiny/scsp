package cn.beardestiny;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author BearDestiny
 * @Date 2023/5/14 3:27
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@MapperScan("cn.beardestiny.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class CarouselApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarouselApplication.class, args);
    }
}
