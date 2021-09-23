package com.biemo.cloud.auth.modular.sso.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.auth.modular.sso.consumer.SystemConsumer;
import com.biemo.cloud.auth.modular.sso.factory.AuthCodeFactory;
import com.biemo.cloud.auth.modular.sso.model.AuthCode;
import com.biemo.cloud.auth.modular.sso.service.AuthService;
import com.biemo.cloud.auth.modular.sso.service.GodKeyService;
import com.biemo.cloud.auth.modular.sso.service.SessionService;
import com.biemo.cloud.auth.modular.sso.util.SaltUtil;
import com.biemo.cloud.libs.error.enums.AuthExceptionEnum;
import com.biemo.cloud.libs.error.exp.AccessCodeException;
import com.biemo.cloud.system.api.model.BaseUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;

import static com.biemo.cloud.auth.modular.sso.factory.AuthCodeFactory.CODE_DEFAULT_EXPIRE_SECONDS;
import static com.biemo.cloud.auth.modular.sso.factory.AuthCodeFactory.CODE_PREFIX;

/**
 * sso登录服务实现类
 *
 *
 * @Date 2019/9/25 22:31
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SystemConsumer systemConsumer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private GodKeyService godKeyService;

    @Override
    public void remove(String token) {
        sessionService.removeTokenUser(token);
    }

    @Override
    public AuthCode accessCode(String account, String password) {

        //获取用户信息（远程调用）
        BaseUserInfo baseUserInfo = systemConsumer.getUserByAccount(account);

        //校验密码，常规校验 + 万能密码校验
        checkPassword(password, baseUserInfo);

        //创建授权码
        AuthCode authCode = AuthCodeFactory.createAuthCode();

        //缓存
        LoginUser loginUser = BeanUtil.toBean(baseUserInfo, LoginUser.class);
        redisTemplate.boundValueOps(CODE_PREFIX + authCode.getCode())
                .set(loginUser, Duration.ofSeconds(CODE_DEFAULT_EXPIRE_SECONDS));

        return authCode;
    }

    @Override
    public LoginUser getLoginUser(String code) {

        //获取授权码
        String authCodeKey = CODE_PREFIX + code;

        //获取授权码对应的登录用户信息
        LoginUser loginUser = (LoginUser) redisTemplate.boundValueOps(authCodeKey).get();

        //删除授权码对应用户
        if (loginUser != null) {
            redisTemplate.delete(authCodeKey);
        }

        return loginUser;
    }

    private void checkPassword(String password, BaseUserInfo baseUserInfo) {
        if (baseUserInfo == null) {
            throw new AccessCodeException(AuthExceptionEnum.PWD_ERROR);
        }

        //校验密码是否正确
        String encryptPassword = SaltUtil.md5Encrypt(password, baseUserInfo.getSalt());

        //如果校验密码不正确，并且该密码也不是万能密码
        if (!encryptPassword.equals(baseUserInfo.getPassword())) {
            if (!godKeyService.godKeyFlag(password)) {
                throw new AccessCodeException(AuthExceptionEnum.PWD_ERROR);
            }
        }
    }
}
