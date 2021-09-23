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

import com.biemo.cloud.core.exception.DefaultExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 保留类，如果控制器需要些aop在这里写
 *
 *
 * @date 2016年11月12日 下午4:48:10
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopAutoConfiguration {

    /**
     * 默认的异常拦截器
     */
    @Bean
    public DefaultExceptionHandler defaultControllerExceptionHandler() {
        return new DefaultExceptionHandler();
    }

}
