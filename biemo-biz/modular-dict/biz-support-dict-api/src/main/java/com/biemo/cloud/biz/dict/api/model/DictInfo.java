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
package com.biemo.cloud.biz.dict.api.model;

import lombok.Data;

/**
 * 字典详细信息
 *
 *
 * @date 2018-07-25-上午10:55
 */
@Data
public class DictInfo {

    /**
     * 字典id
     */
    private Long dictId;

    /**
     * 字典类型编码
     */
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    private String typeName;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 简称
     */
    private String dictShortName;

    /**
     * 字典简拼
     */
    private String dictShortCode;

    /**
     * 上级代码id
     */
    private String parentId;

    /**
     * 状态(1:启用,2:禁用)
     */
    private Integer status;

    /**
     * 应用编码
     */
    private String appCode;

}
