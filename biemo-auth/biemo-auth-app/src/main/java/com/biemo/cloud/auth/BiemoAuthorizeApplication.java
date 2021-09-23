package com.biemo.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主启动类
 *
 *
 * @Date 2019/2/15 3:41 PM
 */
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.biemo.cloud.auth.modular.sso.consumer", "com.biemo.cloud.auth.modular.ssomgr.consumer"})
@EnableScheduling
public class BiemoAuthorizeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiemoAuthorizeApplication.class, args);
    }

}
