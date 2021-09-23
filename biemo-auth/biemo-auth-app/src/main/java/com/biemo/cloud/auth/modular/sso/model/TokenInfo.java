package com.biemo.cloud.auth.modular.sso.model;

import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.auth.modular.sso.entity.AuthClient;
import lombok.Data;

/**
 * token信息
 *
 *
 * @Date 2019/9/25 21:51
 */
@Data
public class TokenInfo {
    private String ticket;
    private String token;
    private LoginUser loginUser;
    private AuthClient authClient;
}
