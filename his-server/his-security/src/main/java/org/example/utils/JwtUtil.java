package org.example.utils;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.Claims;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.security.KeyPair;
import java.util.List;

@Component
public class JwtUtil {
    private final JWTSigner jwtSigner;

    public JwtUtil(KeyPair keyPair) {
        this.jwtSigner = JWTSignerUtil.createSigner("rs256", keyPair);
    }

    /**
     * 创建token
     * @param userId 用户id
     * @param ttl token过期时间
     * @return token
     */
    public String createToken(String userId, Duration ttl, List<?> permissionList) {
        // 1.生成jws
        return JWT.create()
                .setPayload("user", userId)
                .setPayload("permission",permissionList)
                .setExpiresAt(new Date(System.currentTimeMillis() + ttl.toMillis()))
                .setSigner(jwtSigner)
                .sign();
    }

    /**
     * 解析token,获取其中id
     * @param token token
     * return 解析刷新token得到的用户信息
     */
    public String parseTokenForUserId(String token) {
        // 1.校验token是否为空
        if (token == null) {
            throw new RuntimeException("未登录");
        }
        // 2.校验并解析jwt
        JWT jwt;
        try {
            jwt = JWT.of(token).setSigner(jwtSigner);
        } catch (Exception e) {
            throw new RuntimeException("无效的token", e);
        }
        // 2.校验jwt是否有效
        if (!jwt.verify()) {
            // 验证失败
            throw new RuntimeException("无效的token");
        }
        // 3.校验是否过期
        try {
            JWTValidator.of(jwt).validateDate();
        } catch (ValidateException e) {
            throw new RuntimeException("token已经过期");
        }
        // 4.数据格式校验
        Object userPayload = jwt.getPayload("user");
        if (userPayload == null) {
            // 数据为空
            throw new RuntimeException("无效的token");
        }

        // 5.数据解析
        try {
            return String.valueOf(userPayload.toString());
        } catch (RuntimeException e) {
            // 数据格式有误
            throw new RuntimeException("无效的token");
        }
    }
    /**
     * 解析token,获取其中id
     * @param token token
     * return 解析刷新token得到的用户信息
     */
    public List<String> parseTokenForPermission(String token) {
        // 1.校验token是否为空
        if (token == null) {
            throw new RuntimeException("未登录");
        }
        // 2.校验并解析jwt
        JWT jwt;
        try {
            jwt = JWT.of(token).setSigner(jwtSigner);
        } catch (Exception e) {
            throw new RuntimeException("无效的token", e);
        }
        // 2.校验jwt是否有效
        if (!jwt.verify()) {
            // 验证失败
            throw new RuntimeException("无效的token");
        }
        // 3.校验是否过期
        try {
            JWTValidator.of(jwt).validateDate();
        } catch (ValidateException e) {
            throw new RuntimeException("token已经过期");
        }
        // 4.数据格式校验
        Object permissionPayload = jwt.getPayload("permission");
        if (permissionPayload == null) {
            // 数据为空
            throw new RuntimeException("无效的token");
        }

        // 5.数据解析
        try {
            List<?> permissionObjects = (List<?>) permissionPayload;
            List<String> permissionList = new ArrayList<>();
            for (Object permissionObject : permissionObjects) {
                if (permissionObject instanceof String) {
                    permissionList.add((String) permissionObject);
                } else {
                    throw new RuntimeException("无效的权限类型");
                }
            }
            return permissionList;
        } catch (RuntimeException e) {
            // 数据格式有误
            throw new RuntimeException("无效的token");
        }
    }

}
