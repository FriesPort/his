package com.example.config;


import cn.hutool.core.util.ArrayUtil;
import com.example.authorization.AuthorizationManager;
import com.example.constant.AuthConstant;
import com.example.filters.AuthGlobalFilter;
import com.example.filters.CorsFilter;
import com.example.filters.JwtAuthenticationTokenFilter;
import com.example.filters.WhitePathFilter;

import com.example.handler.AccessDeniedHandlerImpl;
import com.example.handler.AuthenticationEntryPointImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 网关安全配置
 * @author diandian
 * @version 2.0
 * @data 2024/10/14 15:08
 */
@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class  GatewaySecurityConfig {
    private final AuthorizationManager authorizationManager;
    private final WhitePathConfig whitePathConfig;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;
    private final WhitePathFilter whitePathFilter;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //跨域支持
        http.cors().and().csrf().disable();
        http.addFilterAt(jwtAuthenticationTokenFilter, SecurityWebFiltersOrder.FIRST);
        http.addFilterAt(corsFilter, SecurityWebFiltersOrder.SECURITY_CONTEXT_SERVER_WEB_EXCHANGE);

        http
                .authorizeExchange()
                //白名单配置
                .pathMatchers(ArrayUtil.toArray(whitePathConfig.getUrls(), String.class)).permitAll()
                //OPTIONS预检请求直接放行
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                //鉴权管理器配置
                .anyExchange().access(authorizationManager)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);


        return http.build();
    }





}
