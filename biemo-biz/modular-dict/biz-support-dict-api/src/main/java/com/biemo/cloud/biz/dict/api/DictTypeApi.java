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
package com.biemo.cloud.biz.dict.api;

import com.biemo.cloud.biz.dict.api.entity.DictType;
import com.biemo.cloud.biz.dict.api.model.DictTypeInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 字典类型远程调用接口
 *
 *
 * @date 2018-07-27-上午10:12
 */
@RequestMapping("/api/dictType")
public interface DictTypeApi {

    /**
     * 获取字典类型列表
     *
     * @param dictTypeInfo 查询条件封装
     *
     * @Date 2018/7/25 下午12:36
     */
    @RequestMapping(value = "/getDictTypeList", method = RequestMethod.POST)
    List<DictTypeInfo> getDictTypeList(@RequestBody DictTypeInfo dictTypeInfo, @RequestParam("pageNo") Integer pageNo,
                                       @RequestParam("pageSize") Integer pageSize);

    /**
     * 添加字典类型
     *
     *
     * @Date 2018/7/25 下午1:43
     */
    @RequestMapping(value = "/addDictType", method = RequestMethod.POST)
    void addDictType(@RequestBody DictType dictType);

    /**
     * 修改字典类型
     *
     *
     * @Date 2018/7/25 下午2:28
     */
    @RequestMapping(value = "/updateDictType", method = RequestMethod.POST)
    void updateDictType(@RequestBody DictType dictType);

    /**
     * 删除字典类型
     *
     *
     * @Date 2018/7/25 下午2:43
     */
    @RequestMapping(value = "/deleteDictType", method = RequestMethod.POST)
    void deleteDictType(@RequestParam("dictTypeId") Long dictTypeId);

    /**
     * 修改字典状态
     *
     *
     * @Date 2018/7/25 下午2:43
     */
    @RequestMapping(value = "/updateDictTypeStatus", method = RequestMethod.POST)
    void updateDictTypeStatus(@RequestParam("dictTypeId") Long dictTypeId, @RequestParam("status") Integer status);

}
