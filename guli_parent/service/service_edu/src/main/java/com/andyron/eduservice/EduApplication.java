package com.andyron.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 为了能使用service_base模块中的功能，要指定扩大扫描区域，指定扫描规则，默认只扫描当前模块下的
 * @author andyron
 */
@SpringBootApplication
@ComponentScan("com.andyron")
@EnableDiscoveryClient // nacos注册
@EnableFeignClients // 服务调用
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }

}
