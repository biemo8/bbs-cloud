package com.biemo.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网关服务
 *
 *
 * @Date 2017/11/10 上午11:24
 */
@SpringBootApplication(exclude = {com.alibaba.cloud.sentinel.custom.SentinelAutoConfiguration.class})
public class BiemoGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiemoGatewayApplication.class, args);
    }

}
