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
package com.biemo.cloud.kernel.model.constants;

/**
 * Roses中所有AOP的顺序排序（数字越低越靠前）
 *
 *
 * @date 2018年07月23日 15:25:32
 */
public interface AopSortConstants {

    /**
     * 默认的ExceptionHandler的aop顺序
     */
    int DEFAULT_EXCEPTION_HANDLER_SORT = 200;

    /**
     * 临时保存RequestData的aop
     */
    int REQUEST_DATA_AOP_SORT = 500;

    /**
     * 参数校验为空的aop
     */
    int PARAM_VALIDATE_AOP_SORT = 510;

    /**
     * 控制器调用链的aop
     */
    int CHAIN_ON_CONTROLLER_SORT = 600;

    /**
     * provider的调用链aop
     */
    int CHAIN_ON_PROVIDER_SORT = 610;

    /**
     * consumer的调用链aop
     */
    int CHAIN_ON_CONSUMMER_SORT = 620;

    int REQUEST_LIMIT_AOP_SORT = 100;

}
