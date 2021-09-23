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
package com.biemo.cloud.auth.api;

import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.kernel.model.api.model.TokenReq;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auth服务对外接口
 *
 *
 * @date 2018-02-07 9:57
 */
@RequestMapping("/authService")
public interface AuthService {

    /**
     * 通过token获取用户信息
     *
     *
     * @Date 2018/1/12 16:32
     */
    @RequestMapping(value = "/getLoginUserByToken", method = RequestMethod.POST)
    LoginUser getLoginUserByToken(@RequestBody TokenReq tokenReq);

}
