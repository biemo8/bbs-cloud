package com.biemo.cloud.bbs.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hoxton.SR3配置，多数据源配置因为和单数据源冲突，所以现在默认版本删除了多数据源配置
 * <p>
 *
 *
 * @Date 2017/5/20 21:58
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = {"com.biemo.cloud.bbs.modular.mapper"})
public class SingleDataSourceConfig {

}

