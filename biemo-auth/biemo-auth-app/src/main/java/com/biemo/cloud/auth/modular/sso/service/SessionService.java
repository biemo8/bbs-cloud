package com.biemo.cloud.auth.modular.sso.service;

import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.auth.modular.sso.entity.AuthClient;
import com.biemo.cloud.auth.modular.sso.model.SessionInfo;
import com.biemo.cloud.auth.modular.sso.model.TokenInfo;

import java.util.List;

/**
 * session回话服务
 *
 *
 * @Date 2019/9/25 22:34
 */
public interface SessionService {

    /**
     * 创建会话，并返回会话信息
     *
     * @param client    客户端信息
     * @param loginUser 登录用户
     * @return 会话信息
     * @auther
     * @Date 2019/9/11 15:38
     */
    TokenInfo create(AuthClient client, LoginUser loginUser);

    /**
     * 创建会话，并返回会话信息
     *
     * @param client 客户端信息
     * @return SessionInfo 会话信息
     * @auther
     * @Date 2019/9/11 15:38
     */
    TokenInfo refresh(AuthClient client, LoginUser loginUser, String ticket);

    /**
     * 销毁会话，并返回会话信息
     *
     * @param client 客户端信息
     * @return SessionInfo 会话信息
     * @auther
     * @Date 2019/9/11 15:38
     */
    TokenInfo destroy(AuthClient client);

    /**
     * 从session中获取登录用户
     *
     * @param token token信息
     * @return LoginUser 登录用户
     * @auther
     * @Date 2019/9/11 17:42
     */
    LoginUser getLoginUserByToken(String token);

    /**
     * 判断是否有token缓存
     *
     * @param token token信息
     * @return 是否登录
     * @auther
     * @Date 2019/9/12 9:52
     */
    boolean existTokenCache(String token);

    /**
     * 删除token缓存
     *
     * @param token
     * @auther
     * @Date 2019/9/17 10:26
     */
    void removeTokenUser(String token);

    /**
     * 从session中获取登录用户
     *
     * @param ticket cookie票据
     * @return LoginUser 登录用户
     * @auther
     * @Date 2019/9/11 17:42
     */
    LoginUser getLoginUserByTicket(String ticket);

    /**
     * 判断是否有ticket缓存
     *
     * @param ticket cookie票据
     * @return 是否登录
     * @auther
     * @Date 2019/9/12 9:52
     */
    boolean existTicketCache(String ticket);

    /**
     * 删除ticket缓存
     *
     * @param ticket
     * @auther
     * @Date 2019/9/17 10:26
     */
    void removeTicketUser(String ticket);

    /**
     * 获取会话信息
     *
     * @param param 姓名或账号，可以为空
     * @return 会话信息集合
     *
     * @Date 2019/12/5 16:21
     */
    List<SessionInfo> getSessionInfoList(String param);

    /**
     * 强制用户下线
     *
     * @param ticket 票据
     *
     * @Date 2019/12/5 16:21
     */
    void forcedOffline(String ticket);
}
