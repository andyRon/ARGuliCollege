package com.andyron.vodtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @Author: andyron
 **/
@Configuration
//@PropertySource(value = {"classpath:application.properties"})
@ConfigurationProperties(prefix = "server")
public class TestConfig {
//    @Value("${server.port}")
    private String port;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Autowired
    private Environment env;

    private String ss = "xxx";

    public static void main(String[] args) {
        TestConfig config = new TestConfig();
        System.out.println(config.port);
        System.out.println(config.ss);
    }
}
