package com.biemo.cloud.auth.modular.sso.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.auth.modular.sso.entity.AuthClient;
import com.biemo.cloud.auth.modular.sso.model.SessionInfo;
import com.biemo.cloud.auth.modular.sso.model.TokenInfo;
import com.biemo.cloud.auth.modular.sso.service.SessionService;
import com.biemo.cloud.auth.modular.sso.util.*;
import com.biemo.cloud.core.util.HttpContext;
import com.biemo.cloud.core.util.ToolUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.biemo.cloud.auth.api.constants.AuthConstants.TOKEN_PREFIX;

/**
 * 会话服务实现
 *
 *
 * @Date 2019/9/25 22:31
 */
@Service
@Slf4j
public class SessionServiceImpl implements SessionService {

    static final String TICKET_ID = "ticket-id";
    private static final String TICKET_PREFIX = "AUTH_TICKET_";
    private static final String SESSION_PREFIX = "AUTH_SESSION_";
    private static final Integer DEFAULT_EXPIRATION = 82800;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private IpAddressService ipAddressService;

    @Override
    public TokenInfo create(AuthClient client, LoginUser loginUser) {
        TokenInfo tokenInfo = genSessionInfo(client, loginUser);
        createCookieAndSession(tokenInfo);
        return tokenInfo;
    }

    @Override
    public TokenInfo refresh(AuthClient client, LoginUser loginUser, String ticket) {
        TokenInfo tokenInfo = genSessionInfo(client, loginUser, ticket);
        createCookieAndSession(tokenInfo);
        return tokenInfo;
    }

    @Override
    public TokenInfo destroy(AuthClient client) {
        TokenInfo tokenInfo = new TokenInfo();
        String ticket = getTicket();
        LoginUser loginUser = getLoginUserByTicket(ticket);
        if (loginUser != null) {
            destroySession(loginUser, ticket);
            removeTicketUser(ticket);

            tokenInfo.setAuthClient(client);
            tokenInfo.setTicket(ticket);
            tokenInfo.setLoginUser(loginUser);
        }
        destroyCookie();
        return tokenInfo;
    }

    private void destroySession(LoginUser loginUser, String ticket) {
        String key = SESSION_PREFIX + loginUser.getAccount() + "_" + ticket;
        BoundHashOperations<String, String, Object> hashOperations = redisTemplate.boundHashOps(key);
        Map<String, String> tokens = (Map<String, String>) hashOperations.get("tokens");
        if (tokens != null && tokens.size() > 0) {
            Set<Map.Entry<String, String>> entries = tokens.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String token = entry.getKey();
                String logoutUrl = entry.getValue();
                destroyToken(token);

                // 退出所有子系统
                if (ToolUtil.isNotEmpty(logoutUrl)) {
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("token", token);

                    log.info("调用退出登录的url=" + logoutUrl);
                    JSONObject jsonObject = HttpRequestUtils.httpPost(logoutUrl, jsonParam, token);
                    log.info("调用退出登录的返回数据 data:{}", JSONObject.toJSONString(jsonObject));
                }
            }
        }
        // 删除用户账号缓存信息
        redisTemplate.delete(key);
    }

    private String getTicket() {
        return CookieUtil.get(HttpContext.getRequest(), TICKET_ID);
    }

    private void destroyCookie() {
        Cookie cookie = new Cookie(TICKET_ID, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        CookieUtil.remove(HttpContext.getResponse(), cookie);
    }

    private TokenInfo genSessionInfo(AuthClient client, LoginUser loginUser) {
        // 创建cookie票据ticket
        String ticket = IdWorker.get32UUID() + IdWorker.getIdStr();
        return genSessionInfo(client, loginUser, ticket);
    }

    private TokenInfo genSessionInfo(AuthClient client, LoginUser loginUser, String ticket) {
        String token = generateToken(client, loginUser);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setAuthClient(client);
        tokenInfo.setLoginUser(loginUser);
        tokenInfo.setToken(token);
        tokenInfo.setTicket(ticket);
        return tokenInfo;
    }

    private String generateToken(AuthClient client, LoginUser loginUser) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("accountId", loginUser.getAccountId());
        return JwtTokenUtil.generateToken(loginUser.getAccount(), claims, client.getPrivateKey(), client.getTokenExp());
    }

    private void createCookieAndSession(TokenInfo tokenInfo) {
        createCookie(tokenInfo);
        createToken(tokenInfo);
        createSession(tokenInfo);
    }

    private void createToken(TokenInfo tokenInfo) {
        Integer tokenExp = tokenInfo.getAuthClient().getTokenExp();
        Integer refreshTokenExp = tokenInfo.getAuthClient().getRefreshTokenExp();
        if (refreshTokenExp == null) {
            refreshTokenExp = DEFAULT_EXPIRATION;
        }
        setTicketUser(tokenInfo.getTicket(), tokenInfo.getLoginUser(), refreshTokenExp);
        setTokenUser(tokenInfo.getToken(), tokenInfo.getLoginUser(), tokenExp);
    }

    private void destroyToken(String token) {
        removeTokenUser(token);
    }


    private void createSession(TokenInfo tokenInfo) {
        HttpServletRequest request = HttpContext.getRequest();

        // 将账号生成的所有token缓存起来
        String ip = IpInfoUtils.getIpAddr(request);
        LoginUser loginUser = tokenInfo.getLoginUser();
        String ticket = tokenInfo.getTicket();
        AuthClient authClient = tokenInfo.getAuthClient();
        String key = SESSION_PREFIX + loginUser.getAccount() + "_" + ticket;

        BoundHashOperations<String, String, Object> hashOperations = redisTemplate.boundHashOps(key);
        Map<String, String> tokens = (Map<String, String>) hashOperations.get("tokens");
        log.info("获取登录的应用AppTokenSet:{}", JSONObject.toJSONString(tokens));
        if (tokens != null && tokens.size() > 0) {
            tokens.put(tokenInfo.getToken(), authClient.getLoginOutUrl());
            hashOperations.put("tokens", tokens);
        } else {
            SessionInfo sessionInfo = new SessionInfo();
            sessionInfo.setTicket(ticket);
            sessionInfo.setAccount(loginUser.getAccount());
            sessionInfo.setUserName(loginUser.getName());
            sessionInfo.setIp(ip);
            sessionInfo.setIpAddress(getIpAddress(ip));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String loginTime = dateTimeFormatter.format(LocalDateTime.now());
            sessionInfo.setLoginTime(loginTime);
            Map<String, String> newTokens = new HashMap<>();
            newTokens.put(tokenInfo.getToken(), authClient.getLoginOutUrl());
            sessionInfo.setTokens(newTokens);
            Map<String, Object> sessionMap = BeanUtil.beanToMap(sessionInfo);
            hashOperations.putAll(sessionMap);
        }
        hashOperations.expire(authClient.getTokenExp(), TimeUnit.SECONDS);
    }

    private String getIpAddress(String ip) {
        String ipAddress;
        try {
            ipAddress = ipAddressService.getCityInfo(ip);
        } catch (Exception e) {
            log.error(e.getMessage());
            ipAddress = "未找到";
        }
        return ipAddress;
    }

    private void createCookie(TokenInfo tokenInfo) {
        HttpServletResponse response = HttpContext.getResponse();
        // cookie 中保存ticketId
        Cookie cookie = new Cookie(TICKET_ID, tokenInfo.getTicket());
        cookie.setPath("/");
        Integer refreshTokenExp = tokenInfo.getAuthClient().getRefreshTokenExp();
        if (refreshTokenExp == null) {
            refreshTokenExp = DEFAULT_EXPIRATION;
        }
        cookie.setMaxAge(refreshTokenExp);
        CookieUtil.set(response, cookie);
    }


    @Override
    public LoginUser getLoginUserByToken(String token) {
        return getTokenUser(token);
    }

    @Override
    public LoginUser getLoginUserByTicket(String ticket) {
        return getTicketUser(ticket);
    }

    @Override
    public boolean existTokenCache(String token) {
        return getLoginUserByToken(token) != null;
    }

    @Override
    public boolean existTicketCache(String ticket) {
        return getLoginUserByTicket(ticket) != null;
    }

    @Override
    public void removeTokenUser(String token) {
        redisTemplate.delete(TOKEN_PREFIX + token);
    }

    @Override
    public void removeTicketUser(String ticket) {
        redisTemplate.delete(TICKET_PREFIX + ticket);
    }

    @Override
    public List<SessionInfo> getSessionInfoList(String param) {

        // 正则获取会话信息缓存
        Set<String> keys = redisTemplate.keys(SESSION_PREFIX + "*");

        List<SessionInfo> resultList = new ArrayList<>();
        for (String key : keys) {

            //获取key对应的value值
            BoundHashOperations<String, String, Object> hashOperations = redisTemplate.boundHashOps(key);
            Map<String, Object> map = hashOperations.entries();
            SessionInfo sessionInfo = BeanUtil.toBean(map, SessionInfo.class);
            resultList.add(sessionInfo);
        }

        // 过滤查询属性
        if (StrUtil.isNotEmpty(param)) {
            resultList = resultList
                    .stream()
                    .filter(sessionInfo -> param.equals(sessionInfo.getAccount()) || param.equals(sessionInfo.getUserName()))
                    .collect(Collectors.toList());
        }
        resultList.sort(Comparator.comparing(SessionInfo::getLoginTime).reversed());
        return resultList;
    }

    @Override
    public void forcedOffline(String ticket) {
        LoginUser loginUser = getTicketUser(ticket);
        destroySession(loginUser, ticket);
        removeTicketUser(ticket);
    }

    private void setTicketUser(String ticket, LoginUser loginUser) {
        setTicketUser(ticket, loginUser, DEFAULT_EXPIRATION);
    }

    private void setTicketUser(String ticket, LoginUser loginUser, Integer expire) {
        redisTemplate.boundValueOps(TICKET_PREFIX + ticket).set(loginUser, Duration.ofSeconds(expire));
    }

    private LoginUser getTicketUser(String ticket) {
        return (LoginUser) redisTemplate.boundValueOps(TICKET_PREFIX + ticket).get();
    }

    private void setTokenUser(String token, LoginUser loginUser) {
        setTokenUser(token, loginUser, DEFAULT_EXPIRATION);
    }

    private void setTokenUser(String token, LoginUser loginUser, Integer expire) {
        redisTemplate.boundValueOps(TOKEN_PREFIX + token).set(loginUser, Duration.ofSeconds(expire));
    }

    private LoginUser getTokenUser(String token) {
        if(token!=null&&token.trim().length()>0&&token.trim().startsWith("Bearer")){
            token = token.substring(7);
            return (LoginUser) redisTemplate.boundValueOps(TOKEN_PREFIX + token).get();

        }
        return null;
    }
}
