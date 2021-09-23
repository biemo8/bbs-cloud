package com.biemo.cloud.auth.modular.ssomgr.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 网关信息工具类
 *
 *
 * @Date 2019/12/4 21:16
 */
public class GatewayActuatorUtil {

    public static final List<String> predicates = new ArrayList<>(Arrays.asList("After", "Before", " Between ", "Cookie "
            , "Header", " Host", " Method ", "Path", " Query ", "RemoteAddr"));

    public static final List<String> gatewayFilterFactories = new ArrayList<>(Arrays.asList("AddRequestHeader", "AddRequestParameter", "AddResponseHeader"
            , "DedupeResponseHeader", "Hystrix", "FallbackHeaders", "PrefixPath", "PreserveHostHeader", "RequestRateLimiter", "RedirectTo"
            , "RemoveHopByHopHeadersFilter", "RemoveRequestHeader", "RemoveResponseHeader", "RewritePath", "RewriteResponseHeader", "SaveSession"
            , "SecureHeaders", "SetPath", "SetResponseHeader", "SetStatus", "StripPrefix", "Retry", "RequestSize", "ModifyRequestBody", "ModifyResponseBody"
            , "JwtToken", "PermissionAuth"));

    /**
     * 获取路由谓词
     *
     *
     * @Date 2019/12/4 21:16
     */
    public static List<String> getRoutePredicates() {
        return GatewayActuatorUtil.predicates;
    }

    /**
     * 获取GatewayFilter Factories
     *
     *
     * @Date 2019/12/4 21:16
     */
    public static List<String> getGatewayFilterFactories() {
        return GatewayActuatorUtil.gatewayFilterFactories;
    }
}
