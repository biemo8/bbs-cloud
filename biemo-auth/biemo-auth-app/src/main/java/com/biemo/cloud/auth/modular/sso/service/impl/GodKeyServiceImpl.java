package com.biemo.cloud.auth.modular.sso.service.impl;

import com.biemo.cloud.auth.modular.sso.service.GodKeyService;
import com.biemo.cloud.core.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 万能密码实现
 *
 *
 * @Date 2019/12/8 21:31
 */
@Service
public class GodKeyServiceImpl implements GodKeyService {

    /**
     * 存储万能密码相关的缓存key（hash结构）
     */
    private static final String GOD_KEY_HASH_CACHE_KEY = "GOD_KEY_TICKET";

    /**
     * 存储万能密码状态的key
     */
    private static final String GOD_KEY_STATUS_CACHE_KEY = "godKeyStatus";

    /**
     * 存储万能密码值的key
     */
    private static final String GOD_KEY_VALUE_CACHE_KEY = "godKeyValue";

    /**
     * 万能密码状态的枚举值
     */
    private static final String ENABLE_STATUS = "enable";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean enableFlag() {
        Map<Object, Object> godKeyMap = redisTemplate.opsForHash().entries(GOD_KEY_HASH_CACHE_KEY);
        if (ToolUtil.isEmpty(godKeyMap)) {
            return false;
        }
        String status = (String) godKeyMap.get(GOD_KEY_STATUS_CACHE_KEY);
        return ENABLE_STATUS.equals(status);
    }

    @Override
    public boolean godKeyFlag(String password) {
        if (ToolUtil.isEmpty(password)) {
            return false;
        }

        Map<Object, Object> godKeyMap = redisTemplate.opsForHash().entries(GOD_KEY_HASH_CACHE_KEY);
        if (ToolUtil.isEmpty(godKeyMap)) {
            return false;
        }

        String godKeyValue = (String) godKeyMap.get(GOD_KEY_VALUE_CACHE_KEY);
        return password.equals(godKeyValue);
    }

    @Override
    public String getGodKey() {
        Map<Object, Object> godKeyMap = redisTemplate.opsForHash().entries(GOD_KEY_HASH_CACHE_KEY);
        if (ToolUtil.isEmpty(godKeyMap)) {
            return null;
        }
        return (String) godKeyMap.get(GOD_KEY_VALUE_CACHE_KEY);
    }

}
