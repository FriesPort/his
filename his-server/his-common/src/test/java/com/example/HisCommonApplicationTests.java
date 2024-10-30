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
        String token="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1c2VyIjoidTEiLCJwZXJtaXNzaW9uIjpbIlBFUk1JU1NJT05fYWRtaW4iXSwiZXhwIjoxNzMwMjk0NTI1fQ.ggJFaNAx-6LZrFK9rNifxhCbiMeGNilMEuRcLvE6xamYwTb3UehWxCVTD6Si8PRQJHCSyGUWFU2ux3R4ZTOR6gr_7f2EHYYoojuZNWeQqKwD44q3zL5X5YJCy65bn6gx5xd_U3Y81NvpoZZUC1bY13ZoPqjMQ2SxJFKUwBLex5uk3opkiU9eQ_Z_oBNEQhlOZ1jrKjXXwI02ALXtwWcQatdJbD3QGpmPVM1IRW-2rlHZAPb5KODSzIY8Jk0jLnwMSUXP56uq-PqDSRgqRd7F1RuuZxdHAlsdUGUt9-FtgU4FOMUW7I0wCHBoZoW27_K5A-IA90GHnv-wAREibuDMOg";
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
