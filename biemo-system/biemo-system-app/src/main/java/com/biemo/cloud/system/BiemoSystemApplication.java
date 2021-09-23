package com.biemo.cloud.system;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 系统管理基础服务
 *
 *
 * @Date 2019/5/16 21:39
 */
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.biemo.cloud.system.modular.sys.consumer", "com.biemo.cloud.system.modular.ent.consumer"})
@SpringBootApplication
public class BiemoSystemApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BiemoSystemApplication.class).run(args);
    }

}


