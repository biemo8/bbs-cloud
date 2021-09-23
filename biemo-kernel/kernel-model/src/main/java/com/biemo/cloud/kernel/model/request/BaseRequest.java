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
package com.biemo.cloud.kernel.model.request;

import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础请求参数封装，用于控制器中的接口
 *
 *
 * @Date 2019-09-28 18:09
 */
@Data
public class BaseRequest implements BaseValidatingParam, Serializable {

    /**
     * 每页显示数量
     */
    private Long pageSize;

    /**
     * 第几页
     */
    private Long pageNo;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 正序或者倒序排列（asc 或 desc）
     */
    private String sort;

}
