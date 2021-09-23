package com.biemo.cloud.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 监控中心启动类
 *
 * @author makesoft
 * @Date 2018/6/24 22:28
 */


@EnableAdminServer
@SpringBootApplication
@EnableDiscoveryClient
public class BiemoSpringBootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiemoSpringBootAdminApplication.class, args);
    }
}

