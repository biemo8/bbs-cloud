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

import com.biemo.cloud.gateway.core.filters.AddAuthHeaderFilter;
import com.biemo.cloud.gateway.core.filters.RequestNoFilter;
import com.biemo.cloud.gateway.core.filters.factory.JwtTokenGatewayFilterFactory;
import com.biemo.cloud.gateway.core.filters.factory.PermissionAuthGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器的配置
 *
 *
 * @date 2017-11-08-下午3:23
 */
@Configuration
public class FilterConfig {

    /**
     * 请求号header过滤器
     *
     *
     * @Date 2019/5/12 14:47
     */
    @Bean
    public RequestNoFilter requestNoFilter() {
        return new RequestNoFilter();
    }

    /**
     * 添加auth header过滤器
     *
     *
     * @Date 2019/5/12 14:47
     */
    @Bean
    public AddAuthHeaderFilter authHeaderFilter() {
        return new AddAuthHeaderFilter();
    }

    /**
     * token校验过滤器
     *
     *
     * @Date 2019/5/12 14:47
     */
    @Bean
    public JwtTokenGatewayFilterFactory jwtTokenGatewayFilterFactory() {
        return new JwtTokenGatewayFilterFactory();
    }

    /**
     * 权限资源校验过滤器
     *
     *
     * @Date 2019/5/12 14:47
     */
    @Bean
    public PermissionAuthGatewayFilterFactory permissionAuthGatewayFilterFactory() {
        return new PermissionAuthGatewayFilterFactory();
    }

}
