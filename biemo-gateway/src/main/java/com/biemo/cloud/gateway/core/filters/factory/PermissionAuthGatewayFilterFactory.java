package com.biemo.cloud.gateway.core.filters.factory;

import com.biemo.cloud.gateway.modular.validate.impl.PermissionValidateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

import static com.biemo.cloud.gateway.core.consts.GatewayConstants.AUTH_HEADER;

/**
 * 资源权限校验过滤器（针对于部分路由，非全局过滤器）
 * <p>
 * ps: GatewayFilterFactory结尾的过滤器可以在application.yml中配置
 *
 *
 * @Date 2019/5/12 11:53
 */
public class PermissionAuthGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Autowired
    private PermissionValidateServiceImpl permissionValidateService;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {

            //当前请求的路径
            ServerHttpRequest request = exchange.getRequest();
            String currentRequestPath = request.getPath().toString();

            //获取请求头的Auth token
            String authToken = request.getHeaders().getFirst(AUTH_HEADER);

            //权限校验
            permissionValidateService.validate(authToken, currentRequestPath);

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

}
