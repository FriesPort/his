package com.example.config.Jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "hm.jwt")
public class JwtProperties {
    private Resource location;
    private String password="hmall123";
    private String alias;
    private Duration tokenTTL = Duration.ofMinutes(10);
}
