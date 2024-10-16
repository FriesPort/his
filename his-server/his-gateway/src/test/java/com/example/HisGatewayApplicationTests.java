package com.example;


import com.example.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = HisGatewayApplication.class)
class HisGatewayApplicationTests {


    @Autowired
    RedisCache redisCache;
    @Test
    void contextLoads() {

    }
}
