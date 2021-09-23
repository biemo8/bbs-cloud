package com.biemo.cloud.gateway.core.consts;

/**
 * 网关过滤器的顺序
 *
 *
 * @Date 2019/5/14 22:23
 */
public class GatewayFilterOrdered {

    /**
     * 请求号过滤器的顺序
     */
    public static final int REQUEST_NO_ORDER = -100;

    /**
     * 添加auth头的过滤器的顺序
     */
    public static final int ADD_AUTH_HEADER_ORDER = -200;

}
