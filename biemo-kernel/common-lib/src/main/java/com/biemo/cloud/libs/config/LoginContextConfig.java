package com.biemo.cloud.libs.config;

import com.biemo.cloud.auth.api.AuthService;
import com.biemo.cloud.auth.api.context.LoginContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 获取当前登录用户
 *
 *
 * @Date 2020/2/11 22:33
 */
@Configuration
public class LoginContextConfig {

    @Bean
    public LoginContext loginContext(AuthService authService) {
        return new LoginContext(authService);
    }

}
