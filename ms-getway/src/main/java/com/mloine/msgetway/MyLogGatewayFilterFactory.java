package com.mloine.msgetway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 *  @Author: XueYongKang
 *  @Description:    过滤器工厂
 *      约定 GatewayFilterFactory结尾
 *  @Data: 2019/12/20 10:52
 */
@Component
public class MyLogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyLogGatewayFilterFactory.class);


    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange,chain) -> {
            LOGGER.info("请求进来了 key = {}, valie = {}",config.getName(),config.getValue() );

            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().build();

            exchange.mutate().request(modifiedRequest).build();

            return chain.filter(exchange);
        };


    }
}
