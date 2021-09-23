/**
 * Copyright  2018-2020 &   (admin@makesoft.cn)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.biemo.cloud.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.biemo.cloud.core.datascope.DataScopeInterceptor;
import com.biemo.cloud.core.dbid.BiemoDatabaseIdProvider;
import com.biemo.cloud.core.metadata.CustomMetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Properties;

/**
 * MybatisPlus配置
 *
 * @author makesoft
 * @Date 2021/08/20 21:58
 */
@ConditionalOnProperty(prefix = "spring.datasource", name = "url")
@Configuration(proxyBeanMethods = false)
public class MybatisPluginAutoConfiguration {

    /**
     * 自定义公共字段自动注入
     */
    @Bean
    @ConditionalOnMissingBean
    public MetaObjectHandler metaObjectHandler() {
        return new CustomMetaObjectHandler();
    }
    /**
     * 数据范围mybatis插件
     */
    @Bean
    @ConditionalOnMissingBean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }
    /**
     * 数据库id选择器
     */
    @Bean
    @ConditionalOnMissingBean
    public BiemoDatabaseIdProvider biemoDatabaseIdProvider() {
        return new BiemoDatabaseIdProvider();
    }

//    @Bean
//    ConfigurationCustomizer mybatisConfigurationCustomizer() {
//        return new ConfigurationCustomizer() {
//            @Override
//            public void customize(org.apache.ibatis.session.Configuration configuration) {
//                configuration.addInterceptor(new com.github.pagehelper.PageInterceptor());
//            }
//        };
//    }



}
