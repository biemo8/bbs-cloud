package com.biemo.cloud.auth.modular.sso.model;

import lombok.Data;

import java.util.Map;

/**
 * 会话信息
 *
 *
 * @Date 2019/9/25 21:51
 */
@Data
public class SessionInfo {
    private Map<String, String> tokens;
    private String account;
    private String userName;
    private String ticket;
    private String ip;
    private String ipAddress;
    private String loginTime;
}
