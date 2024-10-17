package com.example;

import com.example.utils.IdGenerate;
import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

@SpringBootTest
class HisCommonApplicationTests {

    @Autowired
    IdGenerate idGenerate;
    @Autowired
    JwtUtil jwtTool;
    @Autowired
    RedisCache redisCache;
    @Test
    void contextLoads() {
        List<String> permissionList = List.of("user:add", "user:update");
        String token=jwtTool.createToken("1", Duration.ofMinutes(10),permissionList);
        System.out.println("token:"+token);
        String relToken=token.replace("Bearer ","");
        String userId=jwtTool.parseTokenForUserId(relToken);
        System.out.println("userid"+userId);
        List<String> permissionList1=jwtTool.parseTokenForPermission(relToken);
        System.out.println("permissionList:"+permissionList1);
        String permissions=permissionList1.toString();
        redisCache.setCacheObject("permissions:",permissions);
    }

}
