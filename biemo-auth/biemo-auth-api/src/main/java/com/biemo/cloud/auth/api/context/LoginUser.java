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


import com.biemo.cloud.kernel.model.api.model.BaseLoginUser;
import lombok.Data;

/**
 * 当前用户的登录信息
 *
 *
 * @Date 2018/8/22 下午6:19
 */
@SuppressWarnings("ALL")
@Data
public class LoginUser extends BaseLoginUser {

    /**
     * 账号id
     */
    private Long accountId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 公司id
     */
    private Long companyId;

}
