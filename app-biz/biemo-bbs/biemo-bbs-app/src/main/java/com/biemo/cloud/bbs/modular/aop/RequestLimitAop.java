package com.biemo.cloud.bbs.modular.aop;

import com.biemo.cloud.bbs.modular.annotation.RequestLimit;
import com.biemo.cloud.core.constant.Constants;
import com.biemo.cloud.core.util.redis.RedisCache;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class RequestLimitAop {

    @Autowired
    RedisCache redisCache;

    /**
     * 限制 一段时间可以请求的次数
     * @param proceedingJoinPoint
     * @param requestLimit
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(requestLimit)")
    public Object requestLimit(ProceedingJoinPoint proceedingJoinPoint, RequestLimit requestLimit) throws Throwable {
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(Constants.AUTH_HEADER);
        String methodName = proceedingJoinPoint.getSignature().getName();
        StringBuffer redisKey = new StringBuffer();
        redisKey.append("requestLimit:").append(methodName).append(":").append(token);
        Integer requestCount = redisCache.getCacheObject(redisKey.toString());
        requestCount=requestCount==null?0:requestCount;
        Object proceed =null;
        if(requestCount<requestLimit.count()){
            redisCache.setCacheObject(redisKey.toString(),requestCount+1,requestLimit.time(), TimeUnit.SECONDS);
            proceed = proceedingJoinPoint.proceed();
        }else{
            return ResponseData.error("操作频繁，请 "+redisCache.getExpire(redisKey.toString(),TimeUnit.SECONDS)+" 秒后重试");
        }
        return proceed;
    }
}
