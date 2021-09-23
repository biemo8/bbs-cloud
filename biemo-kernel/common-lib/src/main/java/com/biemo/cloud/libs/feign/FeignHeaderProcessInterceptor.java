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
package com.biemo.cloud.libs.feign;

import com.biemo.cloud.core.util.HttpContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * feign远程调用添加header的过滤器
 *
 *
 * @date 2018-05-07-下午7:25
 */
@Slf4j
public class FeignHeaderProcessInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = HttpContext.getRequest();

        if (request == null) {
            if (log.isDebugEnabled()) {
                log.debug("被调环境中不存在request对象，则不往header里添加当前请求环境的header!");
            }
            return;
        } else {
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();

                    //过滤掉Accept Header
                    if (name.equalsIgnoreCase("Accept")) {
                        continue;
                    }

                    String values = request.getHeader(name);
                    requestTemplate.header(name, values);
                }
            }
        }
        this.addOtherHeaders(requestTemplate);
    }

    public void addOtherHeaders(RequestTemplate requestTemplate) {

    }
}
