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
package com.biemo.cloud.kernel.logger.util;

import com.biemo.cloud.core.context.RequestDataHolder;
import com.biemo.cloud.core.request.RequestData;
import com.biemo.cloud.core.util.SpringContextHolder;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.logger.chain.context.TraceIdHolder;
import com.biemo.cloud.kernel.logger.config.properties.LogProperties;
import com.biemo.cloud.kernel.logger.entity.SendingCommonLog;
import com.biemo.cloud.kernel.logger.service.LogProducerService;
import com.biemo.cloud.kernel.model.auth.AbstractLoginUser;
import com.biemo.cloud.kernel.model.auth.context.AbstractLoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 日志记录工具
 *
 *
 * @date 2018-01-16 15:00
 */
public class LogUtil {

    private static LogProducerService getLogProducer() {
        return SpringContextHolder.getBean(LogProducerService.class);
    }

    private static LogProperties getLogProperties() {
        return SpringContextHolder.getBean(LogProperties.class);
    }

    public static void info(String message) {
        doLog(LogLevel.INFO, RequestDataHolder.get(), message, null);
    }

    public static void error(String message, Throwable exception) {
        doLog(LogLevel.ERROR, RequestDataHolder.get(), message, exception);
    }

    public static void debug(String message) {
        doLog(LogLevel.DEBUG, RequestDataHolder.get(), message, null);
    }

    public static void trace(String message) {
        doLog(LogLevel.TRACE, RequestDataHolder.get(), message, null);
    }

    public static void warn(String message) {
        doLog(LogLevel.WARN, RequestDataHolder.get(), message, null);
    }

    public static void debug(String requestNo, String message) {
        doLog(requestNo, LogLevel.DEBUG, RequestDataHolder.get(), message, null);
    }

    public static void info(String requestNo, String message) {
        doLog(requestNo, LogLevel.INFO, RequestDataHolder.get(), message, null);
    }

    /**
     * 记录日志(不需要传递请求号)
     *
     *
     * @Date 2018/1/16 15:02
     */
    private static void doLog(LogLevel level, RequestData requestData, String message, Throwable exception) {
        doLog(TraceIdHolder.get(), level, requestData, message, exception);
    }

    /**
     * 记录日志(需要传递请求号，目前用在网关)
     *
     *
     * @Date 2018/1/16 15:02
     */
    private static void doLog(String requestNo, LogLevel level, RequestData requestData, String message, Throwable exception) {

        //获取被调用的类的名称
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        String className = null;
        String methodName = null;

        if (stack.length < 5) {
            className = LogUtil.class.getName();
        } else {
            className = stack[4].getClassName();
            methodName = stack[4].getMethodName();
        }

        //记录日志
        try {
            Logger logger = LoggerFactory.getLogger(Class.forName(className));
            switch (level) {
                case INFO:
                    logger.info(createMessage(requestData, message));
                    break;
                case WARN:
                    logger.warn(createMessage(requestData, message));
                    break;
                case DEBUG:
                    logger.debug(createMessage(requestData, message));
                    break;
                case ERROR:
                    logger.error(createMessage(requestData, message), exception);
                    break;
                case TRACE:
                    logger.trace(createMessage(requestData, message));
                    break;
                default:
                    logger.debug(createMessage(requestData, message));
                    break;
            }

            //将日志记录到数据库
            if (isWriteLog(level)) {

                SendingCommonLog log = new SendingCommonLog();

                if (requestData != null) {
                    if (requestData.getData() != null) {
                        log.setRequestData(requestData.getData().toJSONString());
                    }
//                    log.setIp(ToolUtil.getIP());
                    log.setUrl(requestData.getUrl());
                }

                //获取当前登录用户
                try {
                    AbstractLoginContext bean = SpringContextHolder.getBean(AbstractLoginContext.class);
                    AbstractLoginUser loginUser = bean.getLoginUser();
                    log.setAppCode(loginUser.getAppId().toString());
                    log.setAccountId(loginUser.getUserUniqueId() != null ? loginUser.getUserUniqueId().toString() : null);
                } catch (Exception e) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("当前没有登录用户！");
                    }
                }

                log.setRequestNo(requestNo);
                log.setClassName(className);
                log.setMethodName(methodName == null ? "" : methodName);
                log.setLevel(level.name());
                log.setLogContent(message);
                log.setCreateTimestamp(System.currentTimeMillis());
                log.setAppCode(ToolUtil.getApplicationName());

                if (level.equals(LogLevel.ERROR) && exception != null) {
                    log.setLogContent(getErrorInfoFromException(exception));
                }
                getLogProducer().sendMsg(log);
            }

        } catch (ClassNotFoundException e) {
            Logger logger = LoggerFactory.getLogger(LogUtil.class);
            logger.error("记录日志出错,找不到调用类名称!", e);
        }
    }

    /**
     * 堆栈异常信息转化为字符串形式
     *
     *
     * @date 2018-05-02 16:23
     */
    private static String getErrorInfoFromException(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            e.printStackTrace(pw);
            return sw.getBuffer().toString().replaceAll("\\$", "T");
        } catch (Exception e2) {
            return "ErrorInfoFromException";
        } finally {
            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            pw.close();
        }
    }

    private static boolean isWriteLog(LogLevel level) {

        LogProperties logProperties = getLogProperties();

        //判断当前请求的日志级别是否在配置文件中开启
        String logLevelPropertes = logProperties.getLevel();
        if (ToolUtil.isEmpty(logLevelPropertes)) {
            logLevelPropertes = "info,error";
        }
        logLevelPropertes = logLevelPropertes.toUpperCase();
        String levelName = level.name();

        return logLevelPropertes.contains(levelName);
    }

    /**
     * 创建日志内容
     */
    private static String createMessage(RequestData requestData, String message) {
        String requestNo = TraceIdHolder.get();
        return "RequestNo: " +
                requestNo +
                " ==>> " +
                "Messages: " +
                message +
                " ==>> RequestData: " +
                requestData;
    }

    /**
     * 日志记录级别
     */
    public enum LogLevel {
        INFO, ERROR, WARN, DEBUG, TRACE
    }

}
