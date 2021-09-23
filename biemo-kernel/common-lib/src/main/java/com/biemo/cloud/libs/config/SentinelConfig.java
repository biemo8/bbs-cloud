package com.biemo.cloud.libs.config;

import com.biemo.cloud.libs.sentinel.RestfulUrlCleaner;
import com.biemo.cloud.libs.sentinel.SentinelBlockHandler;
import com.biemo.cloud.libs.sentinel.SentinelOriginParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sentinel配置
 *
 *
 * @Date 2020/2/11 21:20
 */
@Configuration
public class SentinelConfig {

    @Bean
    public RestfulUrlCleaner restfulUrlCleaner() {
        return new RestfulUrlCleaner();
    }

    @Bean
    public SentinelBlockHandler sentinelBlockHandler() {
        return new SentinelBlockHandler();
    }

    @Bean
    public SentinelOriginParser sentinelOriginParser() {
        return new SentinelOriginParser();
    }

}
