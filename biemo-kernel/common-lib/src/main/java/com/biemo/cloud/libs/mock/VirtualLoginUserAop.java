package com.biemo.cloud.libs.mock;

import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.core.util.HttpContext;
import com.biemo.cloud.kernel.model.auth.context.LoginUserHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

/**
 * 虚拟的当前登陆用户AOP
 *
 *
 * @Date 2019/9/23 13:47
 */
@Aspect
@Order(601)
public class VirtualLoginUserAop {

    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * 拦截控制器层
     */
    @Pointcut("execution(* *..controller.*.*(..))")
    public void cutService() {
    }

    /**
     * <pre>
     *  主要业务逻辑如下：
     *      1. 创建虚拟登陆用户
     *      2. 添加进LoginContext
     * </pre>
     */
    @Around("cutService()")
    public Object vistualLoginUser(ProceedingJoinPoint point) throws Throwable {

        String authorization = HttpContext.getRequest().getHeader("Authorization");

        //如果token传的是1，并且当前profile是dev或者local，则自动生成一个loginUser
        if ("1".equalsIgnoreCase(authorization)) {
            if ("local".equalsIgnoreCase(profile) || "dev".equalsIgnoreCase(profile)) {
                LoginUser loginUser = new LoginUser();

                loginUser.setAccountId(1L);
                loginUser.setCompanyId(1L);
                loginUser.setName("stringstring");
                loginUser.setIdCard("123123");

                loginUser.setUserId(1L);
                loginUser.setAccount("1");
                loginUser.setAppId(1L);
                loginUser.setDeptId(1L);

                loginUser.setRoleIds(null);
                loginUser.setRoleNames(null);
                loginUser.setResourceUrls(null);

                LoginUserHolder.set(loginUser);
            }
        }

        return point.proceed();
    }
}
