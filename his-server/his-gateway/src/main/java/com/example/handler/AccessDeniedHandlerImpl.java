package com.example.handler;

import com.alibaba.fastjson.JSON;
import com.example.vo.JsonVO;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component("accessDeniedHandler")
public class AccessDeniedHandlerImpl implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response=exchange.getResponse();
        JsonVO vo = new JsonVO(500,"权限不足",null);
        String json = JSON.toJSONString(vo);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(json.getBytes())));

    }
}
