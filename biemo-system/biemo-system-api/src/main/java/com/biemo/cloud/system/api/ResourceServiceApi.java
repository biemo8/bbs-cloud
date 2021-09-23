/**
 * Copyright  2021-2025 &   (9094908@qq.com)
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
package com.biemo.cloud.system.api;

import com.biemo.cloud.kernel.model.resource.ResourceDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 资源服务api
 *
 *
 * @Date 2019/12/4 20:54
 */
@RequestMapping("/resourceServiceApi")
public interface ResourceServiceApi {

    /**
     * 保存新增资源
     *
     *
     * @Date 2019/12/4 20:54
     */
    @RequestMapping(name = "保存新增资源", value = "/saveResource", method = RequestMethod.POST)
    void saveResource(@RequestBody ResourceDefinition resourceDefinition);

    /**
     * 修改资源
     *
     *
     * @Date 2019/12/4 20:54
     */
    @RequestMapping(name = "修改资源", value = "/editResource", method = RequestMethod.POST)
    void editResource(@RequestBody ResourceDefinition resourceDefinition);

    /**
     * 删除资源
     *
     *
     * @Date 2019/12/4 20:54
     */
    @RequestMapping(name = "删除资源", value = "/removeResource", method = RequestMethod.GET)
    void removeResource(@RequestParam String resourceId);

    /**
     * 获取应用下拉列表
     *
     *
     * @Date 2019/12/4 20:54
     */
    @RequestMapping(name = "获取应用下拉列表", value = "/getAppSelect", method = RequestMethod.GET)
    List<Map<String, Object>> getAppSelect();

    /**
     * 获取资源所属模块下拉列表
     *
     *
     * @Date 2019/12/4 20:54
     */
    @RequestMapping(name = "获取资源所属模块下拉列表", value = "/getResModuleSelect", method = RequestMethod.GET)
    List<Map<String, Object>> getResModuleSelect();
}
