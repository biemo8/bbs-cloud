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
package com.biemo.cloud.gateway.config;

import com.biemo.cloud.gateway.config.properties.JwtProperties;
import com.biemo.cloud.gateway.core.jwt.JwtTokenUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jwt自动配置
 *
 *
 * @date 2018-02-08 9:49
 */
@Configuration
public class JwtConfig {

    /**
     * jwt token的配置
     */
    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }

    /**
     * jwt工具类
     */
    @Bean
    public JwtTokenUtil jwtTokenUtil(JwtProperties jwtProperties) {
        return new JwtTokenUtil(jwtProperties.getSecret(), jwtProperties.getExpiration());
    }
}

