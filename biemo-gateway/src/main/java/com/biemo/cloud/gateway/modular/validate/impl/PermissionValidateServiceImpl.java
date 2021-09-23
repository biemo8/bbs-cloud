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

import com.biemo.cloud.gateway.core.exception.GatewayException;
import com.biemo.cloud.gateway.core.exception.enums.GatewayExceptionEnum;
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

import java.util.Set;

/**
 * 资源权限校验
 *
 *
 * @date 2018-08-13 21:52
 */
@Service
@Slf4j
public class PermissionValidateServiceImpl extends BaseValidateService {

    @Autowired
    private ResourceServiceCache resourceServiceCache;

    @Autowired
    private AuthServiceCache authServiceCache;

    @Override
    public boolean validate(String token, String requestPath) {

        ResourceUrlReq resourceUrlReq = new ResourceUrlReq();
        resourceUrlReq.setUrl(requestPath);
        token = token!=null&&!"null".equals(token)&&token.length()>7?token.substring(7):token;
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

            //判断本接口是否需要url资源过滤
            if (currentResource.getRequiredPermission()) {

                TokenReq tokenReq = new TokenReq();
                tokenReq.setToken(token);

                //获取当前登录用户
                LoginUser loginUserByToken = authServiceCache.getLoginUserByToken(tokenReq);
                if (loginUserByToken == null) {
                    log.warn("当前登录用户在redis缓存中不存在：{}", tokenReq);
                    throw new GatewayException(GatewayExceptionEnum.TOKEN_EXPIRED);
                }

                //当前用户的资源权限
                Set<String> permissionUrls = loginUserByToken.getResourceUrls();

                //判断当前用户是否有该资源的权限
                if (permissionUrls == null || permissionUrls.size() == 0) {
                    log.warn("当前用户没有该资源的权限：permissionUrls为空");
                    throw new GatewayException(GatewayExceptionEnum.NO_PERMISSION);
                }
                boolean hasPermission = permissionUrls.contains(requestPath);
                if (hasPermission) {
                    return true;
                } else {
                    log.warn("当前用户没有该资源的权限，用户：{},权限列表：{}", token, permissionUrls);
                    throw new GatewayException(GatewayExceptionEnum.NO_PERMISSION);
                }
            } else {

                //本接口不需要资源过滤，直接返回true
                return true;
            }
        }
    }

}
