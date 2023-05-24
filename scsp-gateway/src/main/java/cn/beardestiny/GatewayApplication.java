package cn.beardestiny;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Title: GatewayApplication
 * @Author BearDestiny
 * @Package cn.beardestiny
 * @Date 2023/4/6 16:59
 * @description: 网关启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
