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
package com.biemo.cloud.kernel.logger.chain.context;

import com.biemo.cloud.core.util.HttpContext;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.constants.SystemConstants;
import com.biemo.cloud.kernel.model.request.AbstractBaseRequest;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * header中的spanId的上下文,获取上个请求的spanId，和holder的区别是，holder放的是本应用的spanId
 *
 *
 * @date 2018-05-09-下午6:25
 */
@Slf4j
public class SpanIdContext {

    /**
     * 通过http请求的header中获取spanId
     */
    public static String getSpanIdByHttpHeader() {
        HttpServletRequest request = HttpContext.getRequest();

        if (request == null) {
            if (log.isDebugEnabled()) {
                log.info("获取spanId失败，当前不是http请求环境！");
            }
            return "";
        } else {
            String requestNo = request.getHeader(SystemConstants.SPAN_ID_HEADER_NAME);
            if (ToolUtil.isEmpty(requestNo)) {
                return "";
            } else {
                return requestNo;
            }
        }
    }

    /**
     * 通过请求参数获取spanId，参数必须是AbstractBaseRequest的子类
     */
    public static String getSpanIdByRequestParam(Object[] params) {

        if (params == null || params.length <= 0) {
            return "";
        } else {
            for (Object paramItem : params) {
                if (paramItem instanceof AbstractBaseRequest) {
                    AbstractBaseRequest abstractBaseRequest = (AbstractBaseRequest) paramItem;
                    return abstractBaseRequest.getSpanId();
                }
            }
            return "";
        }
    }

}
