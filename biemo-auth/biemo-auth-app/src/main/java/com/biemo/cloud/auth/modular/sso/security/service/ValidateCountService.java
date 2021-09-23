package com.biemo.cloud.auth.modular.sso.security.service;

import com.biemo.cloud.auth.modular.sso.security.exception.SecurityException;
import com.biemo.cloud.auth.modular.sso.util.IpInfoUtils;
import com.biemo.cloud.core.util.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 校验请求次数的服务（防止同一时间过多请求）
 *
 *
 * @Date 2019/2/19 4:35 PM
 */
@Service
public class ValidateCountService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private BlackListService blackListService;

    /**
     * 登陆次数的缓存标识
     */
    private static final String LOGIN = "_LOGIN";
    private static final Long LOGIN_SECONDS = 60L;

    /**
     * 接口操作次数的缓存标识（通用）
     */
    private static final String OPERATE = "_OPERATE";
    private static final Long OPERATE_SECONDS = 1L;

    /**
     * 短信接口操作次数的缓存标识（通用）
     */
    private static final String SEND_OPERATE = "_SEND_OPERATE";
    private static final Long SEND_OPERATE_SECONDS = 86400L;
    private static final String SEND_URL = "/sca/authority/sendMessage";

    /**
     * hash对象中的key,存储缓存时候的秒数
     */
    private static final String RECORD_SECONDS = "recordSeconds";

    /**
     * hash对象中的key,执行的记录数
     */
    private static final String COUNT_NUMBER = "countNumber";

    /**
     * ----------------------------------------------------------------------
     * 校验时间窗内 登陆的次数，一分钟5次
     *
     * @return 缓存中已经记录的登陆次数，没有记录就是0
     * ----------------------------------------------------------------------
     */
    public Integer validateLogin() {
        return commonValidate(LOGIN, 5);
    }

    public void recordLogin() {
        commonRecord(LOGIN, LOGIN_SECONDS);
    }

    /**
     * ----------------------------------------------------------------------
     * 校验时间窗内 操作的次数，一秒内25次最大
     *
     * @return 缓存中已经记录的登陆次数，没有记录就是0
     * ----------------------------------------------------------------------
     */
    public Integer validateOperate() {
        return commonValidate(OPERATE, 25);
    }

    public void recordOperate() {
        commonRecord(OPERATE, OPERATE_SECONDS);
    }

    /**
     * ----------------------------------------------------------------------
     * 校验时间窗内 短信发送接口操作的次数，一天内50次最大
     *
     * @return 缓存中已经记录的发送次数，没有记录就是0
     * ----------------------------------------------------------------------
     */
    public Integer validateSendCount() {
        String requestURI = HttpContext.getRequest().getRequestURI();
        if (requestURI.contains(SEND_URL)) {
            return commonValidate(SEND_OPERATE, 50);
        }
        return null;
    }

    public void recordSendCount() {
        String requestURI = HttpContext.getRequest().getRequestURI();
        if (requestURI.contains(SEND_URL)) {

            commonRecord(SEND_OPERATE, SEND_OPERATE_SECONDS);
        }
    }

    /**
     * 通用记录缓存的方法
     *
     *
     * @Date 2019/2/20 11:17 AM
     */
    private void commonRecord(String type, Long operateSeconds) {
        String ip = IpInfoUtils.getIpAddr(HttpContext.getRequest());
        String key = ip + type;

        //当前操作时间的秒数
        long currentSeconds = System.currentTimeMillis() / 1000;

        //记录缓存时的秒数
        Long recordSeconds = (Long) redisTemplate.opsForHash().get(key, RECORD_SECONDS);
        if (recordSeconds == null) {
            recordSeconds = currentSeconds;
        }

        //记录缓存中的当前执行次数
        Integer countNumber = (Integer) redisTemplate.opsForHash().get(key, COUNT_NUMBER);
        if (countNumber == null) {
            countNumber = 0;
        }

        //当前时间和记录时间的差 超过限制的时间段就归零计数，否则就直接加1
        if ((currentSeconds - recordSeconds) == operateSeconds) {
            countNumber = 0;
        } else if ((currentSeconds - recordSeconds) > operateSeconds) {
            countNumber = 0;
        } else if ((currentSeconds - recordSeconds) < operateSeconds) {
            countNumber = countNumber + 1;
        } else if (recordSeconds.equals(currentSeconds)) {
            countNumber = countNumber + 1;
        }

        redisTemplate.opsForHash().put(key, RECORD_SECONDS, currentSeconds);
        redisTemplate.opsForHash().put(key, COUNT_NUMBER, countNumber);
        redisTemplate.expire(key, operateSeconds, TimeUnit.SECONDS);
    }

    /**
     * 通用校验方法
     *
     * @param keyAssign 缓存键的名称
     * @param maxCount  时间窗内最大的校验次数
     *
     * @Date 2019/2/19 4:37 PM
     */
    private Integer commonValidate(String keyAssign, Integer maxCount) {
        String ip = IpInfoUtils.getIpAddr(HttpContext.getRequest());
        String key = ip + keyAssign;

        //记录缓存中的当前执行次数
        Integer countNumber = (Integer) redisTemplate.opsForHash().get(key, COUNT_NUMBER);

        //过期时间
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);

        //如果没有缓存则目前没有记录这个ip
        if (countNumber == null || countNumber.equals(0L)) {
            return 0;
        }

        //如果记录数大于了最大记录数，则当前用户超过了规定的访问量，拉入和名单
        if (countNumber > maxCount) {

            //记录黑名单
            blackListService.addBlackList(ip);
            throw new SecurityException(800, "您访问过于频繁，请稍后再试！剩余时间：" + expire + "秒！");
        } else {
            return countNumber;
        }

    }

}
