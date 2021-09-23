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
package com.biemo.cloud.kernel.logger.chain.aop;

import com.biemo.cloud.core.context.RequestNoContext;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.logger.chain.context.ParentSpanIdHolder;
import com.biemo.cloud.kernel.logger.chain.context.SpanIdContext;
import com.biemo.cloud.kernel.logger.chain.context.SpanIdHolder;
import com.biemo.cloud.kernel.logger.chain.context.TraceIdHolder;
import com.biemo.cloud.kernel.logger.chain.enums.RpcPhaseEnum;
import com.biemo.cloud.kernel.logger.sql.log.SqlHolder;
import com.biemo.cloud.kernel.logger.util.TraceUtil;
import com.biemo.cloud.kernel.model.auth.context.LoginUserHolder;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import static com.biemo.cloud.kernel.model.constants.AopSortConstants.CHAIN_ON_CONTROLLER_SORT;

/**
 * 基于调用链的服务治理系统的设计（控制器层的aop处理）
 *
 *
 * @date 2018年05月15日14:58:44
 */
@Aspect
@Order(CHAIN_ON_CONTROLLER_SORT)
public class ChainOnControllerAop {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截控制器层和远程提供者层
     */
    @Pointcut("execution(* *..controller.*.*(..))")
    public void cutService() {
    }

    /**
     * <pre>
     *  主要业务逻辑如下：
     *      1. 获取traceId，spanId, parentSpanId（header中）(没有就是没走网关)
     *      2. 推送调用链日志，推送接收到网关的请求，响应网关成功或失败的请求
     * </pre>
     */
    @Around("cutService()")
    public Object sessionKit(ProceedingJoinPoint point) throws Throwable {

        MethodSignature methodSignature = null;
        Signature signature = point.getSignature();
        if (signature instanceof MethodSignature) {
            methodSignature = (MethodSignature) signature;
        }

        long begin = System.currentTimeMillis();
        if (logger.isDebugEnabled()) {
            logger.debug("开始记录controller aop耗时！");
        }

        //生成本节点的spanId
        String currentSpanId = IdWorker.getIdStr();
        SpanIdHolder.set(currentSpanId);

        //获取当前节点的parentSpanId
        String parentSpanId = SpanIdContext.getSpanIdByHttpHeader();
        ParentSpanIdHolder.set(parentSpanId);

        //获取traceId
        String traceId = RequestNoContext.getRequestNoByHttpHeader();
        TraceIdHolder.set(traceId);

        //初始化log记录
        SqlHolder.init();

        //初始化临时LoginUser
        LoginUserHolder.init();

        if (logger.isDebugEnabled()) {
            logger.debug("controller aop 获取参数！" + (System.currentTimeMillis() - begin));
        }

        try {

            if (logger.isDebugEnabled()) {
                logger.debug("controller aop 开始执行控制器之前！" + (System.currentTimeMillis() - begin));
            }

            Object result = point.proceed();

            if (logger.isDebugEnabled()) {
                logger.debug("controller aop 执行控制器成功完成！" + (System.currentTimeMillis() - begin));
            }

            return result;

        } catch (Throwable exception) {

            String exceptionMsg = ToolUtil.getExceptionMsg(exception);

            if (logger.isDebugEnabled()) {
                logger.debug("controller aop 记录完错误日志！" + (System.currentTimeMillis() - begin));
            }

            //报告:发送给网关失败的响应
            TraceUtil.trace(methodSignature, RpcPhaseEnum.EP1, traceId, currentSpanId, parentSpanId, exceptionMsg);

            //报告执行错误的sql的信息
            TraceUtil.trace(methodSignature, RpcPhaseEnum.EP1, traceId, currentSpanId, parentSpanId, SqlHolder.getSqlInfoStrings());

            throw exception;

        } finally {
            SpanIdHolder.remove();
            ParentSpanIdHolder.remove();
            TraceIdHolder.remove();
            SqlHolder.cleanTempSqlInfos();
            LoginUserHolder.remove();
        }
    }
}
