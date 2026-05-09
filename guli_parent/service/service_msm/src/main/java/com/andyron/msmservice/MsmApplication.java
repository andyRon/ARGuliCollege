package com.andyron.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 短信服务 (阿里云SMS)
 * @author andyron
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.andyron")
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class, args);
    }
}
