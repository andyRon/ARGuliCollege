package com.andyron.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author andyron
 **/
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) // TODO 临时关闭Spring Security
@EnableDiscoveryClient
@ComponentScan("com.andyron")
@MapperScan("com.andyron.aclservice.mapper")
public class ServiceAclApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAclApplication.class, args);
    }
}
