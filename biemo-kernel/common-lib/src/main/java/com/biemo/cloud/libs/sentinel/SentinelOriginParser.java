package com.biemo.cloud.libs.sentinel;

import com.biemo.cloud.core.util.ToolUtil;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求来源的的获取，两种方式，第一种根据参数，第二种根据header
 *
 *
 * @Date 2019/9/1 16:42
 */
public class SentinelOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {

        //根据parameter参数来判断来源
        String origin = request.getParameter("S-Origin");
        if (ToolUtil.isNotEmpty(origin)) {
            return origin;
        }

        //根据header来判断来源
        String header = request.getHeader("S-Origin");
        if (ToolUtil.isNotEmpty(header)) {
            return header;
        }

        //来源没有则返回default
        return "default";
    }

}
