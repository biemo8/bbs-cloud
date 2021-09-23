package com.biemo.cloud.auth.modular.sso.controller;

import com.biemo.cloud.auth.modular.sso.model.SsoLoginReq;
import com.biemo.cloud.auth.modular.sso.service.SsoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 单点登陆
 *
 *
 * @Date 2019/9/25 22:02
 */
@Controller
@RequestMapping("/sso")
@Slf4j
public class SsoController {

    @Autowired
    private SsoService ssoService;

    /**
     * SSO登录（从别的应用redirect过来的入口）
     *
     *
     * @Date 2019/9/25 22:02
     */
    @RequestMapping("/login")
    public String ssoLogin(@Validated SsoLoginReq ssoLoginReq) {
        return ssoService.ssoLogin(ssoLoginReq);
    }

    /**
     * SSO退出（从别的应用redirect过来的入口）
     *
     *
     * @Date 2019/9/25 22:03
     */
    @RequestMapping("/logout")
    public String ssoLogout(@Validated SsoLoginReq ssoLoginReq) {
        return ssoService.ssoLogout(ssoLoginReq);
    }

}
