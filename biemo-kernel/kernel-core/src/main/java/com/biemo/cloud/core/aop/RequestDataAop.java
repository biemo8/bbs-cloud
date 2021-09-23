/**
 * Copyright  2018-2020 &   (admin@makesoft.cn)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.biemo.cloud.core.aop;

import com.biemo.cloud.core.context.RequestDataHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import static com.biemo.cloud.kernel.model.constants.AopSortConstants.REQUEST_DATA_AOP_SORT;

/**
 * 对控制器调用过程中,提供一种RequestData便捷调用的aop
 * <p>
 * 废弃掉了，因为requestDataConverter和FastjsonConverter都可以存RequestData
 *
 *
 * @date 2016年11月13日 下午10:15:42
 */
@Aspect
@Order(REQUEST_DATA_AOP_SORT)
public class RequestDataAop {

    @Pointcut("execution(* *..controller.*.*(..))")
    public void cutService() {
    }

    @Around("cutService()")
    public Object sessionKit(ProceedingJoinPoint point) throws Throwable {
        Object result;
        try {
            result = point.proceed();
        } finally {
            //清空 RequestDataHolder
            RequestDataHolder.remove();
        }
        return result;
    }
}
