/**
 * Copyright  2018-2020 &   (https://gitee.com/biemo)
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
package com.biemo.cloud.auth.core.error;

import com.biemo.cloud.libs.error.exp.SpsAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 *
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
@Order(-1)
@Slf4j
public class AuthExceptionHandler {

    /**
     * 拦截登录异常
     */
    @ExceptionHandler(SpsAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String authFail(SpsAuthException e, Model model) {
        log.error("登录异常:", e);
        Map<String, Object> renderMap = e.getRenderMap();
        model.addAllAttributes(renderMap);
        model.addAttribute("clientInfo", renderMap);
        model.addAttribute("tips", "账号或密码错误！");
        return "/sps/login.html";
    }

}
