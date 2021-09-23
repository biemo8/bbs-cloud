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
package com.biemo.cloud.gateway.modular.validate.impl;

import cn.hutool.core.util.StrUtil;
import com.biemo.cloud.gateway.core.exception.GatewayException;
import com.biemo.cloud.gateway.core.exception.enums.GatewayExceptionEnum;
import com.biemo.cloud.gateway.core.jwt.JwtTokenUtil;
import com.biemo.cloud.gateway.modular.cache.AuthServiceCache;
import com.biemo.cloud.gateway.modular.cache.ResourceServiceCache;
import com.biemo.cloud.gateway.modular.validate.BaseValidateService;
import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.kernel.model.api.model.ResourceUrlReq;
import com.biemo.cloud.kernel.model.api.model.TokenReq;
import com.biemo.cloud.kernel.model.resource.ResourceDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 纯token验证鉴权服
 *
 *
 * @date 2018-08-13 21:52
 */
@Service
@Slf4j
public class TokenValidateService extends BaseValidateService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ResourceServiceCache resourceServiceCache;

    @Autowired
    private AuthServiceCache authServiceCache;

    @Override
    public boolean validate(String token, String requestPath) {

        ResourceUrlReq resourceUrlReq = new ResourceUrlReq();
        resourceUrlReq.setUrl(requestPath);

        ResourceDefinition currentResource = resourceServiceCache.getResourceByUrl(resourceUrlReq);

        //找不到资源，不允许访问
        if (currentResource == null) {
            log.warn("当前请求url：{}在资源库中不存在", requestPath);
//            if(requestPath.contains("/sso/login")||requestPath.contains("/auth/accessCode")||requestPath.contains("/sso/logout")){
//                return true;
//            }
            return true;
            //throw new GatewayException(GatewayExceptionEnum.CACHE_URL_NULL);
        } else {
            Boolean requiredLogin = currentResource.getRequiredLogin();

            //如果需要登录
            if (requiredLogin) {

                //如果没有token
                if (StrUtil.isEmpty(token)) {
                    throw new GatewayException(GatewayExceptionEnum.TOKEN_EMPTY);
                }

                //判断token是否正确
                token = token.substring(7);
                Boolean tokenFlag = jwtTokenUtil.checkToken(token);
                if (!tokenFlag) {
                    throw new GatewayException(GatewayExceptionEnum.TOKEN_ERROR);
                }

                //获取当前登录用户
                TokenReq tokenReq = new TokenReq();
                tokenReq.setToken(token);
                LoginUser loginUserByToken = authServiceCache.getLoginUserByToken(tokenReq);
                if (loginUserByToken == null) {
                    log.warn("当前登录用户在redis缓存中不存在：{}", tokenReq);
                    throw new GatewayException(GatewayExceptionEnum.TOKEN_EXPIRED);
                }

                return true;
            } else {
                return true;
            }
        }
    }
}
