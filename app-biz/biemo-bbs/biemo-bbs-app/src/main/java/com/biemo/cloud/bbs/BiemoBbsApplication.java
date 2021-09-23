package com.biemo.cloud.bbs;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.biemo.cloud.core.config.MybatisPluginAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(scanBasePackages = {"com.biemo.cloud"})
@EnableTransactionManagement
@EnableCaching
@EnableDiscoveryClient
//@EnableFeignClients(basePackages = {"com.biemo.cloud.bbs.modular.web.cms.consumer"})
@EnableFeignClients
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class BiemoBbsApplication extends ServletInitializer {

	public static void main(String[] args){
		SpringApplication.run(BiemoBbsApplication.class, args);
	}
}
