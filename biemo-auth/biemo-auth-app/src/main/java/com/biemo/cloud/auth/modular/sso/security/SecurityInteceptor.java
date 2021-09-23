/**
 * Copyright  2018-2020 &   (https://gitee.com/biemo)
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
package com.biemo.cloud.auth.modular.sso.security;

import com.biemo.cloud.auth.modular.sso.security.service.BlackListService;
import com.biemo.cloud.auth.modular.sso.security.service.ValidateCountService;
import com.biemo.cloud.auth.modular.sso.util.IpInfoUtils;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 安全校验的过滤器
 *
 *
 * @Date 2019/2/19 4:35 PM
 */
@Component
public class SecurityInteceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityInteceptor.class);

    @Autowired
    private ValidateCountService validateCountService;

    @Autowired
    private BlackListService blackListService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //查看黑名单列表
        String ipAddr = IpInfoUtils.getIpAddr(request);
        log.debug("访问sca-auth接口:{}，ip地址：{}", request.getRequestURL(), ipAddr);

        Set<Object> blackList = blackListService.getBlackList();
        if (blackList.contains(ipAddr)) {
            throw new ServiceException(700, "由于您的非法访问，ip被加入黑名单！");
        }

        //记录接口操作次数缓存，防止暴力破解密码
        validateCountService.recordOperate();

        //校验短信发送接口操作次数，同一个ip，一天内调用短信发送接口50次就拉入黑名单
        validateCountService.validateSendCount();

        //记录短信发送接口操作次数缓存，防止短信接口被刷
        validateCountService.recordSendCount();

        return true;
    }

}
