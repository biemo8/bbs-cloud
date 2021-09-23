package com.biemo.cloud.core.lock;

/**
 * 分布式锁接口
 *
 *
 * @Date 2019-09-25 16:03
 */
public interface DistributedLock {

    /**
     * 获取锁
     */
    boolean acquire();

    /**
     * 释放锁
     */
    void release();

}
