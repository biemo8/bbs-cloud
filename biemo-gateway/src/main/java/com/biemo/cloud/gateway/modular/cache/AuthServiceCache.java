package com.biemo.cloud.gateway.modular.cache;


import com.biemo.cloud.auth.api.constants.AuthConstants;
import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.kernel.model.api.model.TokenReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 鉴权服务 缓存的实现
 *
 *
 * @Date 2019-08-12 18:52
 */
@Service
public class AuthServiceCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取登录用户根据当前登录token
     *
     *
     * @Date 2019-09-11 14:36
     */
    public LoginUser getLoginUserByToken(TokenReq tokenReq) {
        return (LoginUser) redisTemplate.boundValueOps(AuthConstants.TOKEN_PREFIX + tokenReq.getToken()).get();
    }
}
