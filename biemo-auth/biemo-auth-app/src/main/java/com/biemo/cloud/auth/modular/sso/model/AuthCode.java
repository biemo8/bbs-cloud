package com.biemo.cloud.auth.modular.sso.model;

import lombok.Data;

/**
 * authcode获取后的响应
 *
 *
 * @Date 2019/9/25 21:48
 */
@Data
public class AuthCode {

    /**
     * auth code
     */
    private String code;

    /**
     * 获取code时候的时间
     */
    private String time;

    /**
     * 过期时间
     */
    private Long expire;
}
