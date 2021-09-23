package com.biemo.cloud.libs.config;

import com.biemo.cloud.libs.meta.BiemoCustomMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 多数据源配置，多数据源配置因为和单数据源冲突，所以现在默认版本删除了多数据源配置
 * <p>
 *
 *
 * @Date 2017/5/20 21:58
 */
@Configuration
public class MetadataFillConfig {

    /**
     * 数据库中自定义公共字段自动注入
     */
    @Bean
    public BiemoCustomMetaObjectHandler metaObjectHandler() {
        return new BiemoCustomMetaObjectHandler();
    }

}

