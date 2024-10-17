package com.example.filters;

import cn.hutool.core.util.StrUtil;
import com.example.constant.RedisConstant;
import com.example.handler.CommonSender;
import com.example.utils.RedisCache;
import com.example.vo.ResultStatus;
import com.example.entity.LoginUser;
import com.example.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.yaml.snakeyaml.util.UriEncoder;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Resource
    private RedisCache redisCache;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if(StrUtil.isEmpty(token)){
            return chain.filter(exchange);
        }

        String relToken = token.replace("Bearer ","");
        // 判断凭证是否注销
//        if (Boolean.FALSE.equals(redisTemplate.hasKey(RedisConstant.USER_TOKEN+":"+relToken))){
//            return CommonSender.sender(exchange, ResultStatus.UNAUTHORIZED,null);
//        }

        // 解析 token
        String userid;
        try {
            //从token中解析用户信息并设置到Header中去
            List<String> permissionList = jwtUtil.parseTokenForPermission(relToken);
            userid = jwtUtil.parseTokenForUserId(relToken);
            log.info("当前用户权限信息:{}",permissionList.toString());
            ServerHttpRequest request=exchange.getRequest().mutate()
                    .header("user", UriEncoder.encode(permissionList.toString()))
                    .build();
            exchange=exchange.mutate().request(request).build();
        }catch (Exception e){
            e.printStackTrace();
            return Mono.error(new RuntimeException("token 非法"));
        }
        // 从 redis 中获取用户信息
        String redisKey = "login:" + userid;
        // 这里假设 LoginUser 可以转换为 User 对象
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (loginUser == null) {
            return Mono.error(new RuntimeException("用户未登录"));
        }

        // 存入 ReactiveSecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser, null, loginUser.getAuthorities());
        return ReactiveSecurityContextHolder.getContext()
                .map(context ->{
                    context.setAuthentication(authenticationToken);
                    return context;
                })
                .then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
