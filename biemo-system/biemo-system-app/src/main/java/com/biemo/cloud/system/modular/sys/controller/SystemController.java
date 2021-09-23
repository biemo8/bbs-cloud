package com.biemo.cloud.system.modular.sys.controller;

import com.biemo.cloud.auth.api.constants.AuthConstants;
import com.biemo.cloud.auth.api.context.LoginContext;
import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.system.api.TestApi;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.model.response.SuccessResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权相关的控制器
 *
 *
 * @Date 2019/5/18 21:04
 */
@ApiResource(path = "/system")
@RestController
public class SystemController implements TestApi {

    @GetResource(path = "/getUserInfo")
    @ResponseBody
    public ResponseData getUserInfo() {
        LoginUser user = LoginContext.me().getLoginUser();
        return new SuccessResponseData(user);
    }


    @Override
    public ResponseData test(String a) {
        return ResponseData.success(a);
    }
}
