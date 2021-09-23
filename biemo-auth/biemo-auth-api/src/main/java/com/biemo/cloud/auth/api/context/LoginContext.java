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
package com.biemo.cloud.auth.api.context;


import com.biemo.cloud.auth.api.AuthService;
import com.biemo.cloud.auth.api.constants.AuthConstants;
import com.biemo.cloud.core.util.HttpContext;
import com.biemo.cloud.core.util.SpringContextHolder;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.api.model.TokenReq;
import com.biemo.cloud.kernel.model.auth.AbstractLoginUser;
import com.biemo.cloud.kernel.model.auth.context.AbstractLoginContext;
import com.biemo.cloud.kernel.model.auth.context.LoginUserHolder;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.exception.enums.CoreExceptionEnum;
import com.biemo.cloud.kernel.model.request.AbstractBaseRequest;
import com.biemo.cloud.kernel.model.request.RmiRequestHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;


/**
 * 登录信息上下文
 *
 *
 * @date 2018-02-05 16:58
 */
@Slf4j
public class LoginContext implements AbstractLoginContext {

    private static final String NAMES_DELIMETER = ",";

    private AuthService authService;

    public LoginContext(AuthService authService) {
        this.authService = authService;
    }

    public static LoginContext me() {
        return SpringContextHolder.getBean(LoginContext.class);
    }

    /**
     * 获取当前用户的token
     * <p>
     * 先判断header中是否有Authorization字段，
     * 如果header中没有这个字段，则检查http请求参数中是否带token
     * 如果上面都没有，检查context中的参数（dubbo被调用中）有没有传token
     * <p>
     * 如果任意一个地方有这个值，则返回这个值
     * 两个地方都没有token，则抛出没有登录用户异常
     */
    @Override
    public String getCurrentUserToken() {
        HttpServletRequest request = HttpContext.getRequest();

        //如果请求是在http环境下，则有request对象
        if (request != null) {
            String authToken = request.getHeader(AuthConstants.AUTH_HEADER);
            if (ToolUtil.isNotEmpty(authToken)) {
                return authToken;
            } else {
                String token = request.getParameter("token");
                if (ToolUtil.isNotEmpty(token)) {
                    return token;
                }
            }
        } else {

            //http请求为空，则查看是否在requestHolder中是否有token
            AbstractBaseRequest abstractBaseRequest = RmiRequestHolder.get();

            if (abstractBaseRequest != null) {
                String token = abstractBaseRequest.getToken();
                if (ToolUtil.isNotEmpty(token)) {
                    return token;
                }
            }
        }

        //若上面方法都获取不到token，则返回未登录异常！
        //throw new ServiceException(200,"当前没有登录的用户");
        return null;
    }

    /**
     * 获取当前用户
     * <p>
     * 先从ThreadLocal中拿user，如果有值就直接返回，没取到再去调用远程服务,调用完远程服务把获取到的user放到Threadlocal里
     */
    @Override
    public LoginUser getLoginUser() {
        AbstractLoginUser currentUser = LoginUserHolder.get();
        if (currentUser != null) {
            return (LoginUser) currentUser;
        } else {
            String token = getCurrentUserToken();
            TokenReq tokenReq = new TokenReq();
            tokenReq.setToken(token);
            LoginUser loginUser = this.authService.getLoginUserByToken(tokenReq);

            //临时缓存
            LoginUserHolder.set(loginUser);

            return loginUser;
        }
    }

    /**
     * 获取当前登录用户的账户id
     */
    public Long getAccountId() {
        LoginUser loginUser = this.getLoginUser();
        if (loginUser == null) {
            return null;
        } else {
            return loginUser.getAccountId();
        }
    }

    /**
     * 判断当前用户是否是admin
     */
    public static boolean isAdmin() {
        LoginUser loginUser = LoginContext.me().getLoginUser();

        if (loginUser != null) {
            return loginUser.getRoleCodes().contains(AuthConstants.ADMIN_NAME);
        }

        return false;
    }

    /**
     * 验证当前用户是否属于以下任意一个角色。
     *
     * @param roleNames 角色列表
     * @return 属于:true,否则false
     */
    public static boolean hasAnyRoles(String roleNames) {
        boolean hasAnyRole = false;

        LoginUser loginUser = LoginContext.me().getLoginUser();
        Set<String> roleCodes = loginUser.getRoleCodes();

        if (roleNames != null && roleNames.length() > 0) {
            for (String role : roleNames.split(NAMES_DELIMETER)) {
                if (roleCodes.contains(role.trim())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }
        return hasAnyRole;
    }

    /**
     * 判断是否有url的相关权限
     */
    public static boolean hasPermission(String url) {
        boolean hasPermission = false;

        LoginUser loginUser = LoginContext.me().getLoginUser();
        Set<String> resourceUrls = loginUser.getResourceUrls();

        if (ToolUtil.isNotEmpty(url)) {
            if (resourceUrls.contains(url.trim())) {
                hasPermission = true;
            }
        }

        return hasPermission;
    }

}
