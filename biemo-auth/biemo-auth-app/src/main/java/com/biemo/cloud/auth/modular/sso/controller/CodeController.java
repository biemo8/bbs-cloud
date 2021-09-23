package com.biemo.cloud.auth.modular.sso.controller;

import com.biemo.cloud.auth.modular.sso.model.AccessCodeParam;
import com.biemo.cloud.auth.modular.sso.model.AuthCode;
import com.biemo.cloud.auth.modular.sso.service.AuthService;
import com.biemo.cloud.auth.modular.sso.util.RSAUtil;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 登录认证控制器，颁发accessCode
 *
 *
 * @Date 2019/9/25 21:52
 */
@RestController
@RequestMapping("/auth")
public class CodeController {

    @Autowired
    private AuthService authService;

    /**
     * 获取accessCode，校验账号密码是否正确，密码经过rsa对称加密
     *
     *
     * @Date 2019/9/25 22:03
     */
    @PostMapping("/accessCode")
    public ResponseData accessCode(@RequestBody AccessCodeParam accessCodeParam) {
        String decryptPassword = RSAUtil.decrypt(accessCodeParam.getPassword());
        AuthCode authCode = authService.accessCode(accessCodeParam.getAccount(), decryptPassword);
        return ResponseData.success(authCode);
    }

}
