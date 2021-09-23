package com.biemo.cloud.gateway.core.filters;

import cn.hutool.core.util.StrUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.biemo.cloud.gateway.core.consts.GatewayFilterOrdered.ADD_AUTH_HEADER_ORDER;

/**
 * 自动添加auth header的过滤器
 *
 *
 * @Date 2019/5/14 22:24
 */
public class AddAuthHeaderFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return ADD_AUTH_HEADER_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取请求参数中的token，自动加到header中
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        String token = queryParams.getFirst("token");

        //增加请求头中的请求号
        if (StrUtil.isNotEmpty(token)) {
            ServerHttpRequest request = exchange.getRequest().mutate().header("Authorization", token).build();
            return chain.filter(exchange.mutate().request(request).build());
        }

        return chain.filter(exchange.mutate().build());
    }

}
