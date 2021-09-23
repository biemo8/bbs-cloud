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
package com.biemo.cloud.kernel.scanner.config.properties;

import lombok.Data;

/**
 * 扫描的常量
 *
 *
 * @date 2018-01-03 21:39
 */
@Data
public class ScannerProperties {

    /**
     * 资源扫描开关
     */
    private Boolean open;

    /**
     * 被扫描应用的名称
     */
    private String appName;

    /**
     * 应用的编码
     */
    private String appCode;

    /**
     * 链接符号
     */
    private String linkSymbol = "$";

    /**
     * 项目编码（如果您不设置的话，默认使用spring.application.name填充，请不要设置此值，这个值和网关资源过滤有关）
     * <p>
     * 修复一个项目启动的时候会误删别的项目资源的问题
     *
     * @since 2.2.12
     */
    private String projectCode;

}
