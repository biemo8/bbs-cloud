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

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页结果集
 *
 * @author makesoft
 * @Date 2018/7/22 23:00
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -4071521319254024213L;

    private Integer page = 1;// 要查找第几页
    private Integer pageSize = 20;// 每页显示多少条
    private Integer totalPage = 0;// 总页数
    private Long totalRows = 0L;// 总记录数
    private List<T> rows;// 结果集

    public PageResult() {
    }

    public PageResult(IPage<T> page) {
        this.setRows(page.getRecords());
        this.setTotalRows(page.getTotal());
        this.setPage((int) page.getCurrent());
        this.setPageSize((int) page.getSize());
    }

}
