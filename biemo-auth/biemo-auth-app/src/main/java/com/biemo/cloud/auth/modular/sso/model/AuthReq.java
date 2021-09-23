package com.biemo.cloud.auth.modular.sso.model;

import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

/**
 * 登录认证的请求
 *
 *
 * @date 2019-04-19-14:21
 */
@Data
public class AuthReq extends SsoLoginReq implements BaseValidatingParam {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    @Override
    public String checkParam() {

        if (ToolUtil.isOneEmpty(account, password)) {
            return "账号密码为空！";
        }

        if (ToolUtil.isOneEmpty(super.getClientId())) {
            return "参数不合法，应用id或url为空！";
        }

        return null;
    }
}
