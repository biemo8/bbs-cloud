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

import java.util.Date;

/**
 * 请求时间记录日志
 *
 *
 * @Date 2018/7/16 下午5:13
 */
@Data
public class SendingTCLog {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 花费时间（毫秒）
     */
    private Long useTime;

    /**
     * 创建时间
     */
    private Date createTime;

}
