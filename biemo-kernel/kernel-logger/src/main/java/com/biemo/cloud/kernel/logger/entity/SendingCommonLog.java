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
package com.biemo.cloud.kernel.logger.entity;

import lombok.Data;

/**
 * 日志实体类
 *
 *
 * @date 2018-04-24 13:45
 */
@Data
public class SendingCommonLog {

    private static final long serialVersionUID = 1L;

    /**
     * 日志唯一id
     */
    private Long id;

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 日志级别 info，error，warn，debug
     */
    private String level;

    /**
     * 类名
     */
    private String className;

    /**
     * 打日志的方法的名称
     */
    private String methodName;

    /**
     * 远程访问IP地址
     */
    private String ip;

    /**
     * 用户账号id
     */
    private String accountId;

    /**
     * 日志号
     */
    private String requestNo;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求的数据内容
     */
    private String requestData;

    /**
     * 日志详情
     */
    private String logContent;

    /**
     * 创建时间
     */
    private Long createTimestamp;

}
