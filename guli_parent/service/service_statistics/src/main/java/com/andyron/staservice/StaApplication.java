package com.andyron.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author andyron
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.andyron"})
@EnableDiscoveryClient  // nacos注册中心
@EnableFeignClients  // 服务调用
@MapperScan("com.andyron.staservice.mapper")
@EnableScheduling // 开启定时任务
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
