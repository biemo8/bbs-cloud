package com.biemo.cloud.core.lock.impl;

import com.biemo.cloud.core.lock.DistributedLock;
import com.biemo.cloud.core.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis分布式锁
 *
 *
 * @Date 2019-09-25 16:03
 */
@Slf4j
public class RedisLock implements DistributedLock {

    private static StringRedisTemplate redisTemplate;

    /**
     * 锁的键值
     */
    private String lockKey;

    /**
     * 锁超时, 防止线程得到锁之后, 不去释放锁
     */
    private int expireMilliseconds = 15 * 1000;

    /**
     * 锁等待, 防止线程饥饿
     */
    private int timeoutMilliseconds = 15 * 1000;

    /**
     * 是否已经获取锁
     */
    private boolean locked = false;

    public RedisLock(String lockKey) {
        this.lockKey = lockKey;
    }

    public RedisLock(String lockKey, int timeoutMilliseconds) {
        this.lockKey = lockKey;
        this.timeoutMilliseconds = timeoutMilliseconds;
    }

    public RedisLock(String lockKey, int expireMilliseconds, int timeoutMilliseconds) {
        this.lockKey = lockKey;
        this.expireMilliseconds = expireMilliseconds;
        this.timeoutMilliseconds = timeoutMilliseconds;
    }

    public String getLockKey() {
        return this.lockKey;
    }

    @Override
    public synchronized boolean acquire() {

        int timeout = timeoutMilliseconds;

        if (redisTemplate == null) {
            redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        }

        try {
            while (timeout >= 0) {

                long expires = System.currentTimeMillis() + expireMilliseconds + 1;

                // 锁到期时间
                String expiresStr = String.valueOf(expires);

                if (redisTemplate.opsForValue().setIfAbsent(lockKey, expiresStr)) {
                    locked = true;
                    log.info("[1] 成功获取分布式锁!");
                    return true;
                }

                // redis里的时间
                String redisValueTime = redisTemplate.opsForValue().get(lockKey);

                // 判断是否为空, 不为空的情况下, 如果被其他线程设置了值, 则第二个条件判断是过不去的
                if (redisValueTime != null && Long.parseLong(redisValueTime) < System.currentTimeMillis()) {

                    String oldValueStr = redisTemplate.opsForValue().getAndSet(lockKey, expiresStr);

                    // 获取上一个锁到期时间, 并设置现在的锁到期时间
                    // 只有一个线程才能获取上一个线程的设置时间
                    // 如果这个时候, 多个线程恰好都到了这里, 但是只有一个线程的设置值和当前值相同, 它才有权利获取锁
                    if (oldValueStr != null && oldValueStr.equals(redisValueTime)) {
                        locked = true;
                        log.info("[2] 成功获取分布式锁!");
                        return true;
                    }
                }

                timeout -= 100;
                Thread.sleep(100);
            }
        } catch (Exception e) {
            log.error("获取锁出现异常, 必须释放: {}", e.getMessage());
        }

        return false;
    }

    @Override
    public synchronized void release() {

        if (redisTemplate == null) {
            redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        }

        try {
            if (locked) {
                // redis里的时间
                String redisValueTime = redisTemplate.opsForValue().get(lockKey);

                // 校验是否超过有效期, 如果不在有效期内, 那说明当前锁已经失效, 不能进行删除锁操作
                if (redisValueTime != null && Long.parseLong(redisValueTime) > System.currentTimeMillis()) {
                    redisTemplate.delete(lockKey);
                    locked = false;
                    log.info("[3] 成功释放分布式锁!");
                }
            }
        } catch (Exception e) {
            log.error("释放锁出现异常, 必须释放: {}", e.getMessage());
        }
    }
}
