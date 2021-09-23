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

import com.biemo.cloud.biz.dict.api.model.TreeDictInfo;
import com.biemo.cloud.biz.dict.api.entity.Dict;
import com.biemo.cloud.biz.dict.api.model.DictInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 字典远程调用接口
 *
 *
 * @date 2018-07-27-上午10:12
 */
@RequestMapping("/api/dict")
public interface DictApi {

    /**
     * 新增字典
     *
     *
     * @Date 2018/7/25 下午3:17
     */
    @RequestMapping(value = "/addDict", method = RequestMethod.POST)
    void addDict(@RequestBody Dict dict);

    /**
     * 修改字典
     *
     *
     * @Date 2018/7/25 下午3:35
     */
    @RequestMapping(value = "/updateDict", method = RequestMethod.POST)
    void updateDict(@RequestBody Dict dict);

    /**
     * 删除字典
     *
     *
     * @Date 2018/7/25 下午4:53
     */
    @RequestMapping(value = "/deleteDict", method = RequestMethod.POST)
    void deleteDict(@RequestParam("dictId") Long dictId);

    /**
     * 更新字典状态
     *
     *
     * @Date 2018/7/25 下午4:53
     */
    @RequestMapping(value = "/updatDictStatus", method = RequestMethod.POST)
    void updateDictStatus(@RequestParam("dictId") Long dictId, @RequestParam("status") Integer status);

    /**
     * 获取字典列表
     *
     *
     * @Date 2018/7/25 下午5:18
     */
    @RequestMapping(value = "/getDictList", method = RequestMethod.POST)
    List<DictInfo> getDictList(@RequestBody DictInfo dictInfo, @RequestParam("pageNo") Integer pageNo,
                               @RequestParam("pageSize") Integer pageSize);

    /**
     * 获取树形字典列表
     *
     *
     * @Date 2018/7/25 下午5:53
     */
    @RequestMapping(value = "/getTreeDictList", method = RequestMethod.POST)
    List<TreeDictInfo> getTreeDictList(@RequestParam("dictTypeCode") String dictTypeCode);

}
