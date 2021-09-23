package com.biemo.cloud.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心服务
 *
 * @author makesoft
 * @Date 2018/1/22 21:26
 */
@SpringBootApplication
@EnableEurekaServer
public class BiemoRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiemoRegisterApplication.class, args);
    }
}
