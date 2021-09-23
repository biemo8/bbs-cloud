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
package com.biemo.cloud.auth.config.beetl;

import com.biemo.cloud.core.util.ToolUtil;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

/**
 * beetl拓展配置,绑定一些工具类,方便在模板中直接调用
 *
 *
 * @Date 2018/2/22 21:03
 */
public class GroupUtilContext extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
    }
}
