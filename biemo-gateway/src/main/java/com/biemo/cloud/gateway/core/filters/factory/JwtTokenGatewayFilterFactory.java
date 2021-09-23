package com.biemo.cloud.gateway.core.filters.factory;

import com.biemo.cloud.gateway.modular.validate.impl.TokenValidateService;
import org.springframework.beans.BeansException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import static com.biemo.cloud.gateway.core.consts.GatewayConstants.AUTH_HEADER;

/**
 * jwt的网关校验过滤器（针对于部分路由，非全局过滤器）
 * <p>
 * ps: GatewayFilterFactory结尾的过滤器可以在application.yml中配置
 *
 *
 * @Date 2019/5/12 11:53
 */
public class JwtTokenGatewayFilterFactory extends AbstractGatewayFilterFactory implements ApplicationContextAware {

    ApplicationContext applicationContext;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {

            //当前请求的路径
            ServerHttpRequest request = exchange.getRequest();
            String currentRequestPath = request.getPath().toString();

            //获取请求头的Auth token
            String authToken = request.getHeaders().getFirst(AUTH_HEADER);

            //token校验
            TokenValidateService tokenValidateService = applicationContext.getBean(TokenValidateService.class);
            tokenValidateService.validate(authToken, currentRequestPath);

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
