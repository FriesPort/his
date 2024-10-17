package com.example.authorization;

import cn.hutool.core.convert.Convert;
import com.example.constant.AuthConstant;
import com.example.constant.RedisConstant;
import com.example.utils.RedisCache;
import com.example.entity.LoginUser;
import com.example.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RefreshScope
@Component
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Value("${secure.openauthorization}")
    private boolean isOpenAuthorization;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisCache redisCache;

    /**
     * 根据path查询权限列表
     * @param path
     * @return
     */
    private List<String> queryPermissionListByPath(String path){
        Object obj =redisTemplate.opsForHash().get(RedisConstant.PERMISSION_ROUTE,path);
        if(obj==null){
            return new ArrayList<>();
        }
        List<String> authorities = Convert.toList(String.class,obj);
        authorities = authorities.stream().map(permissionName-> AuthConstant.AUTHORITY_PREFIX +permissionName).collect(Collectors.toList());
        return authorities;
    }


    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        if(!isOpenAuthorization){
            return Mono.just(new AuthorizationDecision(true));
        }

        //1 从Redis中获取当前路径可访问角色列表
        String path = authorizationContext.getExchange().getRequest().getURI().getPath();
        String token = authorizationContext.getExchange().getRequest().getHeaders().getFirst("Authorization").replace("His_","");
        List<String> authorities = queryPermissionListByPath(path);
        String relToken=token.replace("Bearer ","");
        // 没有查询到结果，尝试查找通配符
        if (authorities.isEmpty()) {
            // 1.1 处理二级地址, 比如：请求地址为 "/fc/get"，此时匹配 "/fc/**"
            String[] arr = path.split("/");
            String currPath = "/" + arr[1] + "/**";
            authorities = queryPermissionListByPath(currPath);
            // 1.2 处理参数变量，比如请求地址为 "/fc/get/参数变量"，此时匹配 "/fc/get/**"
            if (authorities.isEmpty()) {
                currPath = path.substring(0, path.lastIndexOf("/") + 1) + "**";
                authorities = queryPermissionListByPath(currPath);
            }
        }
        log.info("当前请求路径：{}，可访问角色列表：{}", path, authorities);
        String userId=jwtUtil.parseTokenForUserId(relToken);
        String redisKey="login:"+userId;
        LoginUser loginUser=redisCache.getCacheObject(redisKey);
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                loginUser, null, loginUser.getAuthorities());
        log.info("authenticationToken:{}",authenticationToken);
        log.info("authentication:{}",authentication);
        Mono<Authentication> combinedAuthentication = Mono.just(authenticationToken);
        log.info("authentication:{}",combinedAuthentication);
        //2 认证通过且角色匹配的用户可访问当前路径
        return combinedAuthentication
                // 判断是否认证
                .filter(Authentication::isAuthenticated)
                // 获取权限对象列表(即：角色列表)
                .flatMapIterable(Authentication::getAuthorities)
                // 遍历获取权限对象(即：获取单个角色名)
                .map(GrantedAuthority::getAuthority)
                // 查看当前路径中是否包对应权限（即：是否包含角色名）
                .any(authorities::contains)
                // 根据判断结果构建一个鉴权对象
                .map(AuthorizationDecision::new)
                // 没有权限列表的情况，构建一个无权访问对象
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}


