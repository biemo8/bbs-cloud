/**
 * Copyright  2021-2025 &   (9094908@qq.com)
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
package com.biemo.cloud.auth.config;

import com.biemo.cloud.auth.core.base.BiemoErrorView;
import com.biemo.cloud.libs.config.CommonContextConfig;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 应用配置
 *
 *
 * @Date 2018/7/24 下午3:27
 */
@Configuration
@Import(value = {CommonContextConfig.class})
public class AppConfig {

    /**
     * 默认错误页面，
     */
    @Bean("error")
    public BiemoErrorView error() {
        return new BiemoErrorView();
    }

    @Bean
    public FilterRegistrationBean CorsFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        //注入过滤器
        registrationBean.setFilter(new MyCorsFilter());
        //过滤器名称
        registrationBean.setName("MyCorsFilter");
        //拦截规则
        registrationBean.addUrlPatterns("/*");
        //过滤器顺序
        registrationBean.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);

        return registrationBean;
    }

//    @Bean
//    public CorsWebFilter corsFilter() {
//        //1.添加CORS配置信息
//        CorsConfiguration config = new CorsConfiguration();
//        //1) 允许的域,不要写*，否则cookie就无法使用了
//        config.addAllowedOrigin("http://localhost:81");
//        //2) 是否发送Cookie信息
//        config.setAllowCredentials(true);
//        //3) 允许的请求方式
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("HEAD");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("PATCH");
//        // 4）允许的头信息
//        config.addAllowedHeader("Origin");
//        config.addAllowedHeader("X-Requested-With");
//        config.addAllowedHeader("Accept");
//        config.addAllowedHeader("Authorization");
//        config.addAllowedHeader("X-App-Id");
//        config.addAllowedHeader("X-Token");
//        config.addAllowedHeader("Content-Type");
//        // 4）有效时间
//        config.setMaxAge(3600L);
//
//        //2.添加映射路径，我们拦截一切请求
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource(new PathPatternParser());
//        configSource.registerCorsConfiguration("/**", config);
//
//        //3.返回新的CorsFilter.
//        return new CorsWebFilter(configSource);
//    }


}
