package com.biemo.cloud.biz.dict.config;

import com.biemo.cloud.biz.dict.core.db.DictInitializer;
import com.biemo.cloud.biz.dict.core.db.DictTypeInitializer;
import com.biemo.cloud.biz.dict.modular.controller.DictController;
import com.biemo.cloud.biz.dict.modular.controller.DictTypeController;
import com.biemo.cloud.biz.dict.modular.provider.DictServiceProvider;
import com.biemo.cloud.biz.dict.modular.provider.DictTypeServiceProvider;
import com.biemo.cloud.biz.dict.modular.service.DictService;
import com.biemo.cloud.biz.dict.modular.service.DictTypeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 字典的自动配置
 *
 *
 * @date 2018-07-24-下午5:12
 */
@Configuration
public class DictAutoConfiguration {

    /**
     * 数据库初始化
     */
    @Bean
    public DictInitializer dictInitializer() {
        return new DictInitializer();
    }

    @Bean
    public DictTypeInitializer dictTypeInitializer() {
        return new DictTypeInitializer();
    }

    /**
     * 控制器
     */
    @Bean
    public DictController dictController() {
        return new DictController();
    }

    @Bean
    public DictTypeController dictTypeController() {
        return new DictTypeController();
    }

    /**
     * 服务层
     */
    @Bean
    public DictService dictService() {
        return new DictService();
    }

    @Bean
    public DictTypeService dictTypeService() {
        return new DictTypeService();
    }

    /**
     * 服务提供者
     */
    @Bean
    public DictServiceProvider dictServiceProvider() {
        return new DictServiceProvider();
    }

    @Bean
    public DictTypeServiceProvider dictTypeServiceProvider() {
        return new DictTypeServiceProvider();
    }
}
