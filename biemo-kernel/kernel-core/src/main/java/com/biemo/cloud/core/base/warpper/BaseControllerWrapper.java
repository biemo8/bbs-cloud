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
package com.biemo.cloud.core.base.warpper;

import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 控制器查询结果的包装类基类
 *
 *
 * @date 2017年2月13日 下午10:49:36
 */
public abstract class BaseControllerWrapper {

    private Page<Map<String, Object>> page = null;

    private PageResult<Map<String, Object>> pageResult = null;

    private Map<String, Object> single = null;

    private List<Map<String, Object>> multi = null;

    public BaseControllerWrapper(Map<String, Object> single) {
        this.single = single;
    }

    public BaseControllerWrapper(List<Map<String, Object>> multi) {
        this.multi = multi;
    }

    public BaseControllerWrapper(Page<Map<String, Object>> page) {
        if (page != null && page.getRecords() != null) {
            this.page = page;
            this.multi = page.getRecords();
        }
    }

    public BaseControllerWrapper(PageResult<Map<String, Object>> pageResult) {
        if (pageResult != null && pageResult.getRows() != null) {
            this.pageResult = pageResult;
            this.multi = pageResult.getRows();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T wrap() {

        /**
         * 包装结果
         */
        if (single != null) {
            wrapTheMap(single);
        }
        if (multi != null) {
            for (Map<String, Object> map : multi) {
                wrapTheMap(map);
            }
        }

        /**
         * 根据请求的参数响应
         */
        if (page != null) {
            return (T) page;
        }
        if (pageResult != null) {
            return (T) pageResult;
        }
        if (single != null) {
            return (T) single;
        }
        if (multi != null) {
            return (T) multi;
        }

        return null;
    }

    protected abstract void wrapTheMap(Map<String, Object> map);
}
