package com.biemo.cloud.core.lock.util;

import com.biemo.cloud.core.lock.DistributedLock;
import com.biemo.cloud.core.lock.impl.RedisLock;

/**
 * 分布式锁工具类
 *
 *
 * @Date 2019-09-25 16:04
 */
public class DistributedLockUtil {

    /**
     * 获取分布式锁
     * 默认获取锁15s超时, 锁过期时间15s
     */
    public static DistributedLock getDistributedLock(String lockKey) {
        lockKey = assembleKey(lockKey);
        return new RedisLock(lockKey);
    }

    /**
     * 获取分布式锁
     */
    public static DistributedLock getDistributedLock(String lockKey, int expireMilliseconds) {
        lockKey = assembleKey(lockKey);
        return new RedisLock(lockKey, expireMilliseconds);
    }

    /**
     * 获取分布式锁
     */
    public static DistributedLock getDistributedLock(String lockKey, int expireMilliseconds, int timeoutMilliseconds) {
        lockKey = assembleKey(lockKey);
        return new RedisLock(lockKey, timeoutMilliseconds, expireMilliseconds);
    }

    /**
     * 对 key 进行拼接
     */
    private static String assembleKey(String lockKey) {
        return String.format("sync_lock_%s", lockKey);
    }

}
