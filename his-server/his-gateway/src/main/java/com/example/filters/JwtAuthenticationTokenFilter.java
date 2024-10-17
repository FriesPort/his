package com.example.filters;



import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import com.example.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.security.web.server.authorization.AuthorizationContext;

@Component("jwtFilter")
@Slf4j
public class JwtAuthenticationTokenFilter implements WebFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 获取 token，这里假设从请求头中获取
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (!org.springframework.util.StringUtils.hasText(token)) {
            return chain.filter(exchange);
        }
        String relToken = token.replace("Bearer ","");
        // 解析 token
        String userid;
        try {
            userid = jwtUtil.parseTokenForUserId(relToken);
        } catch (Exception e) {
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
        System.out.println("存入ReactiveSecurityContextHolder");
        // 存入 ReactiveSecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser, null, loginUser.getAuthorities());
        Mono<SecurityContext> mono=ReactiveSecurityContextHolder.getContext()
                .map(context -> {
                    context.setAuthentication(authenticationToken);
                    return context;
                });
        log.info("权限：{}",mono);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        return mono.then(chain.filter(exchange));
    }
}
