package com.biemo.cloud.bbs.modular.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @date : 2020-09-01 9:31
 * @description :
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {
    /**
     * 请求次数
     */
    int count() default 1;

    /**
     * 限制时间（秒）
     */
    int time() default 5;



}
