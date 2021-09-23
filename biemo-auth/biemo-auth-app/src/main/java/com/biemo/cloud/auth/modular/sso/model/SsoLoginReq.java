package com.biemo.cloud.auth.modular.sso.model;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * sso登录的请求
 *
 *
 * @date 2019-04-19-14:21
 */
@Data
@ToString
public class SsoLoginReq {

    /**
     * 客户端应用id
     */
    @NotNull
    private Long clientId;

    /**
     * 服务器登录成功后的url
     */
    private String redirectUrl;

    /**
     * access code
     */
    private String code;

}
