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
package com.biemo.cloud.auth.modular.sso.provider;

import com.biemo.cloud.auth.api.AuthService;
import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.auth.modular.sso.service.SessionService;
import com.biemo.cloud.kernel.model.api.model.TokenReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权服务
 *
 *
 * @date 2018-08-06-上午9:05
 */
@RestController
public class AuthServiceProvider implements AuthService {

    @Autowired
    private SessionService sessionService;

    @Override
    public LoginUser getLoginUserByToken(@RequestBody TokenReq tokenReq) {
        return sessionService.getLoginUserByToken(tokenReq.getToken());
    }
}
