package com.biemo.cloud.auth.modular.sso.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 黑名单校验服务
 *
 *
 * @Date 2019/2/19 4:35 PM
 */
@Service
public class BlackListService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * sca的黑名单列表
     */
    private static final String SCA_BLACK_LIST = "SCA_BLACK_LIST";

    /**
     * 添加黑名单
     *
     *
     * @Date 2019/2/19 6:03 PM
     */
    public void addBlackList(String ip) {
        redisTemplate.boundSetOps(SCA_BLACK_LIST).add(ip);
    }

    /**
     * 获取黑名单列表
     *
     *
     * @Date 2019/2/20 9:05 AM
     */
    public Set<Object> getBlackList() {
        return redisTemplate.boundSetOps(SCA_BLACK_LIST).members();
    }

    /**
     * 移除黑名单列表
     *
     *
     * @Date 2019/2/20 9:05 AM
     */
    public void removeFromBlackList(Object... ips) {
        redisTemplate.boundSetOps(SCA_BLACK_LIST).remove(ips);
    }
}
