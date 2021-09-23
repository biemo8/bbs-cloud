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
package com.biemo.cloud.system.config;

import com.biemo.cloud.libs.config.CommonContextConfig;
import com.biemo.cloud.core.base.controller.GlobalErrorView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 默认web配置
 *
 *
 * @Date 2018/7/24 下午3:27
 */
@Configuration
@Import(CommonContextConfig.class)
public class AppConfig {

    /**
     * 默认错误页面，返回json
     */
    @Bean("error")
    public GlobalErrorView error() {
        return new GlobalErrorView();
    }

}


