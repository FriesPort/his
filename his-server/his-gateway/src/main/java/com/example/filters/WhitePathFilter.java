package com.example.filters;

import cn.hutool.core.util.StrUtil;
import com.example.config.WhitePathConfig;
import com.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
@Component
public class WhitePathFilter implements WebFilter {
    @Resource
    WhitePathConfig whitePathConfig;
    @Autowired
    JwtUtil jwtUtil;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("白名单过滤器");
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String token = request.getHeaders().getFirst("Authorization");
        List<String> permissions=new ArrayList<>();
        String userid=null;
        if(!StrUtil.isEmpty(token)){
            String relToken = token.replace("Bearer ","");
            permissions= jwtUtil.parseTokenForPermission(relToken);
            userid=jwtUtil.parseTokenForUserId(relToken);
        }

        PathMatcher pathMatcher =  new org.springframework.util.AntPathMatcher();
        List<String> whiteUrls= whitePathConfig.getUrls();
        for(String whiteUrl:whiteUrls){
            if (pathMatcher.match(whiteUrl, uri.getPath())||permissions.contains("PERMISSION_admin")) {
                request = exchange.getRequest().mutate()
                        .header("Authorization", "")
                        .header("userId",userid)
                        .build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }

        return chain.filter(exchange);
    }
}
