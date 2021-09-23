package com.biemo.cloud.system.api.model;

import lombok.Data;

/**
 * 基础用户信息
 *
 *
 * @date 2019-09-11-16:49
 */
@Data
public class UserInfo {

    /**
     * 账号id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户账号
     */
    private String account;


}
