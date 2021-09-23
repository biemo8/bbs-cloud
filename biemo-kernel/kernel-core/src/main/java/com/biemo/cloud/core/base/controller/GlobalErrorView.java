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
package com.biemo.cloud.core.base.controller;

import com.biemo.cloud.kernel.model.exception.enums.CoreExceptionEnum;
import com.biemo.cloud.kernel.model.response.ErrorResponseData;
import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 错误页面的默认跳转(例如请求404的时候,默认走这个视图解析器)
 *
 *
 * @date 2017-05-21 11:34
 */
public class GlobalErrorView implements View {

    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public void render(Map<String, ?> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json");

        if (map != null && map.get("code") != null && map.get("message") != null) {
            httpServletResponse.getWriter().write(JSON.toJSONString(new ErrorResponseData((Integer) map.get("code"), (String) map.get("message"))));
        } else {
            if (map != null && map.get("status") != null && map.get("error") != null) {
                Object status = map.get("status");
                Object error = map.get("error");
                httpServletResponse.getWriter().write(JSON.toJSONString(new ErrorResponseData((Integer) status, (String) error)));
            } else {
                httpServletResponse.getWriter().write(JSON.toJSONString(new ErrorResponseData(CoreExceptionEnum.PAGE_NULL.getCode(), CoreExceptionEnum.PAGE_NULL.getMessage())));
            }
        }
    }
}
