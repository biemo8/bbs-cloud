package com.biemo.cloud.gateway.core.filters;

import com.biemo.cloud.gateway.core.consts.GatewayFilterOrdered;
import com.biemo.cloud.kernel.model.constants.SystemConstants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * 请求号过滤器(全局过滤器)
 *
 *
 * @Date 2019/5/14 22:24
 */
public class RequestNoFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return GatewayFilterOrdered.REQUEST_NO_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //生成唯一请求号uuid
        String requestNo = UUID.randomUUID().toString();

        //增加请求头中的请求号
        ServerHttpRequest request = exchange.getRequest().mutate().header(SystemConstants.REQUEST_NO_HEADER_NAME, requestNo).build();

        //增加响应头的请求号
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(SystemConstants.REQUEST_NO_HEADER_NAME, requestNo);

        return chain.filter(exchange.mutate().request(request).response(response).build());
    }

}
