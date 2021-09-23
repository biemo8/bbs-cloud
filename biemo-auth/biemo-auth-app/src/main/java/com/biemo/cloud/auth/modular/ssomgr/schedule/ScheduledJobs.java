package com.biemo.cloud.auth.modular.ssomgr.schedule;

import cn.hutool.core.util.RandomUtil;
import com.biemo.cloud.core.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.biemo.cloud.auth.modular.ssomgr.constants.GodKeyConst.*;


/**
 * 定时任务执行类
 *
 *
 * @Date 2019/12/7 18:13
 */
@Slf4j
@Component
public class ScheduledJobs {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 定时刷新万能密码
     *
     *
     * @Date 2019/12/7 18:13
     */
    @Scheduled(fixedDelay = REFRESH_INTERVAL)
    public void refreshGodKey() {
        Map<Object, Object> godKeyMap = redisTemplate.opsForHash().entries(GOD_KEY_TICKET);
        if (ToolUtil.isEmpty(godKeyMap)) {
            log.info("初始化万能密码中...");
            String godKey = generateGodKey(16);
            godKeyMap.put(GOD_KEY_STATUS, ENABLE_GOD_KEY);
            godKeyMap.put(GOD_KEY_VALUE, godKey);
            redisTemplate.opsForHash().putAll(GOD_KEY_TICKET, godKeyMap);
            //设置失效时间
            redisTemplate.expire(GOD_KEY_TICKET, System.currentTimeMillis() + REFRESH_INTERVAL, TimeUnit.SECONDS);
            log.info("初始化万能密码完成！ 密码为：" + godKey);
            return;
        }
        log.info("刷新万能密码过程中...");
        String godKey = generateGodKey(16);
        godKeyMap.put(GOD_KEY_VALUE, godKey);
        redisTemplate.opsForHash().putAll(GOD_KEY_TICKET, godKeyMap);
        redisTemplate.expire(GOD_KEY_TICKET, System.currentTimeMillis() + REFRESH_INTERVAL, TimeUnit.SECONDS);
        log.info("刷新万能密码完成！密码为：" + godKey);
    }

    /**
     * 生成万能密码
     *
     *
     * @Date 2019/12/7 18:13
     */
    public String generateGodKey(int length) {
        String baseChars = RandomUtil.BASE_CHAR + RandomUtil.BASE_CHAR.toUpperCase();
        return RandomUtil.randomString(baseChars, length);
    }
}
