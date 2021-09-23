/**
 * Copyright  2018-2020 &   (admin@makesoft.cn)
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
package com.biemo.cloud.kernel.validator.util;

import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.util.ValidateUtil;
import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;

/**
 * 校验参数中的参数是否符合规则
 *
 *
 * @date 2018-08-07-上午10:34
 */
public class CheckUtil {

    public static void validateParameters(Object[] methodParams) {
        for (Object methodParam : methodParams) {
            if (methodParam instanceof BaseValidatingParam) {
                BaseValidatingParam baseValidatingParam = (BaseValidatingParam) methodParam;
                String checkResult = baseValidatingParam.checkParam();

                //如果校验结果不为空，则代表参数校验有空的或者不符合规则的，并且checkResult为参数错误的提示信息
                if (ValidateUtil.isNotEmpty(checkResult)) {
                    throw new RequestEmptyException(checkResult);
                }
            }
        }
    }
}
