package com.example.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.security.KeyPair;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KeyPair keyPair(JwtProperties properties){
        // 获取秘钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(
                        properties.getLocation(),
                        properties.getPassword().toCharArray());
        //读取钥匙对
        return keyStoreKeyFactory.getKeyPair(
                properties.getAlias(),
                properties.getPassword().toCharArray());
    }

//    private static final List<String> ALLOWED_HEADERS=
//            Arrays.asList("Content-Type", "Authorization", "Sec-Fetch-Mode");
//    private static final List<String> ALLOWED_METHODS =
//            Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS");
//    @Bean
//    public CorsFilter corsFilter(){
//        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config=new CorsConfiguration();
//        config.setAllowedOrigins(Arrays.asList("http://111.230.250.144:9090"));
//        config.setAllowedHeaders(ALLOWED_HEADERS);
//        config.setAllowedMethods(ALLOWED_METHODS);
//        config.setAllowCredentials(true);
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
}