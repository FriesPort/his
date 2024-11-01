package com.example.filters;

import cn.hutool.core.util.StrUtil;
import com.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminFilter implements WebFilter {
    @Autowired
    JwtUtil jwtUtil;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        System.out.println("AdminFilter");
        String token = request.getHeaders().getFirst("Authorization");
        List<String> permissions=new ArrayList<>();
        String userid=null;
        if(!StrUtil.isEmpty(token)){
            String relToken = token.replace("Bearer ","");
            permissions= jwtUtil.parseTokenForPermission(relToken);
            userid=jwtUtil.parseTokenForUserId(relToken);
        }
        if(permissions.contains("PERMISSION_admin")){
            request = exchange.getRequest().mutate()
                    .header("Authorization", token)
                    .header("role","admin")
                    .header("userId",userid)
                    .build();
        }
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);

    }
}
