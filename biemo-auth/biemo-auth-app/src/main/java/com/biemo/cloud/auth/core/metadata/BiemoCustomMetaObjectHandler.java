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
package com.biemo.cloud.auth.core.metadata;

import com.biemo.cloud.auth.api.context.LoginContext;
import com.biemo.cloud.core.metadata.CustomMetaObjectHandler;

/**
 * 自定义sql字段填充器,本类默认在default-config.properties中配置
 * <p>
 * 若实际项目中，字段名称不一样，可以新建一个此类，在yml配置中覆盖mybatis-plus.global-config.metaObject-handler配置即可
 * <p>
 * 注意默认获取的userId为空
 *
 *
 * @Date 2018/7/4 下午12:42
 */
public class BiemoCustomMetaObjectHandler extends CustomMetaObjectHandler {

    /**
     * 获取用户唯一id（注意默认获取的用户唯一id为空，如果想填写则需要继承本类）
     */
    @Override
    public Object getUserUniqueId() {
        try {
            return LoginContext.me().getAccountId();
        } catch (Exception e) {
            return -100L;
        }
    }
}
