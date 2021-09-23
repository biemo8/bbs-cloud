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
package com.biemo.cloud.kernel.model.page;

import lombok.Data;

/**
 * 分页查询的请求参数封装
 *
 *
 * @date 2017-11-08-上午11:18
 */
@Data
public class PageQuery {

    /**
     * 每页的条数
     */
    private Integer pageSize;

    /**
     * 页编码(第几页)
     */
    private Integer pageNo;

    /**
     * 排序方式(asc 或者 desc)
     */
    private String sort;

    /**
     * 排序的字段名称
     */
    private String orderByField;

    public PageQuery() {
    }

    public PageQuery(Integer pageSize, Integer pageNo, String sort, String orderByField) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.sort = sort;
        this.orderByField = orderByField;
    }
}
