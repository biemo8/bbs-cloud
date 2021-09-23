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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * 应用api
 *
 *
 * @Date 2019/12/4 20:54
 */
@RequestMapping("/systemAppApi")
public interface SystemAppApi {

    /**
     * 获取应用id和name映射map
     *
     *
     * @Date 2019/12/4 20:55
     */
    @RequestMapping(name = "获取应用id和name映射map", value = "/getAppMap", method = RequestMethod.GET)
    Map<Long, String> getAppMap();

    /**
     * 获取应用下拉列表
     *
     *
     * @Date 2019/12/4 20:55
     */
    @RequestMapping(name = "应用下拉列表", value = "/getAppSelectList", method = RequestMethod.GET)
    List<Map<String, Object>> getAppSelect();

}
