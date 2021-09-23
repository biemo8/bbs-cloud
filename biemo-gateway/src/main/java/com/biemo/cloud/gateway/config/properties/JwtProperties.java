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
package com.biemo.cloud.gateway.config.properties;

/**
 * jwt相关配置
 *
 *
 * @date 2018年2月7日 10:10:06
 */
public class JwtProperties {

    /**
     * jwt的秘钥，注意修改！
     */
    private String secret = "u47REBDLrxfd79w";

    /**
     * jwt过期时间(单位:秒)(默认:1天)
     */
    private Long expiration = 82800L;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

}
