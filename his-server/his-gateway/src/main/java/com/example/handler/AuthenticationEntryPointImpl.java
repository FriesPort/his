package com.example.handler;

import com.alibaba.fastjson.JSON;
import com.example.vo.JsonVO;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component("authenticationEntryPoint")
public class AuthenticationEntryPointImpl implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response=exchange.getResponse();
        JsonVO vo = new JsonVO(500,"用户认证失败，token过期或者签名错误",null);
        String json = JSON.toJSONString(vo);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(json.getBytes())));
    }
}
