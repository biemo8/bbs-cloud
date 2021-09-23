package com.biemo.cloud.libs.sentinel;

import com.biemo.cloud.libs.sentinel.enums.BlockExceptionEnums;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * sentinel错误响应的格式化
 *
 *
 * @Date 2019/9/1 15:51
 */
public class SentinelBlockHandler implements UrlBlockHandler {

    @Override
    public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws IOException {

        BlockExceptionEnums blockExceptionEnums = null;

        if (ex instanceof FlowException) {

            //限流异常
            blockExceptionEnums = BlockExceptionEnums.FLOW_EXCEPTION;

        } else if (ex instanceof DegradeException) {

            //降级异常
            blockExceptionEnums = BlockExceptionEnums.DEGRADE_EXCEPTION;

        } else if (ex instanceof ParamFlowException) {

            //热点参数限流
            blockExceptionEnums = BlockExceptionEnums.PARAM_FLOW_EXCEPTION;

        } else if (ex instanceof SystemBlockException) {

            //系统规则
            blockExceptionEnums = BlockExceptionEnums.SYSTEM_BLOCK_EXCEPTION;

        } else if (ex instanceof AuthorityException) {

            //授权规则不通过
            blockExceptionEnums = BlockExceptionEnums.AUTHORITY_EXCEPTION;

        } else {

            //未知流控
            blockExceptionEnums = BlockExceptionEnums.FLOW_EXCEPTION;

        }

        // http状态码
        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setContentType("application/json;charset=utf-8");

        JSON.writeJSONString(response.getWriter(), blockExceptionEnums.getErrorResponseData());
    }
}
