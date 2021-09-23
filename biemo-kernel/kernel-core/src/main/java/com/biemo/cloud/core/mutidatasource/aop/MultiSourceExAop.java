package com.biemo.cloud.core.mutidatasource.aop;

import com.biemo.cloud.core.mutidatasource.DataSourceContextHolder;
import com.biemo.cloud.core.mutidatasource.annotion.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

/**
 * 多数据源切换的aop
 *
 *
 * @date 2017年3月5日 上午10:22:16
 */
@Aspect
public class MultiSourceExAop implements Ordered {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String[] names = null;

    /**
     * 构造函数
     *
     * @param names 数据源的名称们，第一个为默认名称
     */
    public MultiSourceExAop(String... names) {
        this.names = names;
    }

    @Pointcut(value = "@annotation(com.biemo.cloud.core.mutidatasource.annotion.DataSource)")
    private void cut() {

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        //获取被aop拦截的方法
        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        //获取方法上的DataSource注解
        DataSource datasource = currentMethod.getAnnotation(DataSource.class);

        //如果有DataSource注解，则设置DataSourceContextHolder为注解上的名称
        if (datasource != null) {
            DataSourceContextHolder.setDataSourceType(datasource.name());
            log.debug("设置数据源为：" + datasource.name());
        } else {
            DataSourceContextHolder.setDataSourceType(names[0]);
            log.debug("设置数据源为：dataSourceCurrent");
        }

        try {
            return point.proceed();
        } finally {
            log.debug("清空数据源信息！");
            DataSourceContextHolder.clearDataSourceType();
        }
    }


    /**
     * aop的顺序要早于spring的事务
     */
    @Override
    public int getOrder() {
        return 1;
    }

}
