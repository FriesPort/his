package com.example;


import com.example.mapper.UserMapper;
import com.example.entity.LoginUser;
import com.example.entity.User;
import com.example.mapper.PermissionMapper;
import com.example.utils.JwtUtil;


import com.example.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
class HisSecurityApplicationTests {
    @Autowired
    private JwtUtil jwtTool;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RedisCache redisCache;
    @Test
    void contextLoads() {
        User user=userMapper.selectById("u1");
        List<String> permissionList=userMapper.SearchPermissionByUserId(user.getId());
        LoginUser loginUser=new LoginUser(user,permissionList);
        redisCache.setCacheObject("login:ut",loginUser);
        LoginUser loginUser1=redisCache.getCacheObject("login:ut");
        System.out.println(loginUser1);
    }

}
