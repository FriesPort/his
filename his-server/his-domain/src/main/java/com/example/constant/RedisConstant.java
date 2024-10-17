package com.example.constant;

public interface RedisConstant {
    /**
     * 权限和功能路由的映射
     */
    String PERMISSION_ROUTE = "AUTH:PERMISSION_ROUTE";

    /**
     * token 存入redis的key前缀
     */
    String USER_TOKEN = "AUTH:USER_TOKEN";
}
