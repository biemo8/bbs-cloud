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
 * 配置前缀的常量集合
 *
 *
 * @Date 2018/7/23 下午5:41
 */
public interface ConfigPrefixConstants {

    /**
     * 日志记录配置的前缀，属性见LogProperties
     */
    String LOG_PREFIX = "roses.log";

    /**
     * 资源扫描器的前缀
     */
    String SCANNER_PREFIX = "roses.scanner";

    /**
     * 阿里云的oss文件存储
     */
    String ALIYUN_OSS = "aliyun.oss";
}
