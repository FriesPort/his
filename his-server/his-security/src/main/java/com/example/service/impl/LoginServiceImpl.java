package com.example.service.impl;

import com.example.config.Jwt.JwtProperties;
import com.example.constant.AuthConstant;
import com.example.constant.RedisConstant;
import com.example.dto.login.LoginDTO;
import com.example.entity.LoginUser;
import com.example.entity.PermissionPathMap;
import com.example.mapper.PermissionMapper;
import com.example.service.LoginService;
import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import com.example.vo.login.LoginVO;
import lombok.extern.slf4j.Slf4j;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword());
        Authentication authenticate= authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出返回信息
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，使用userid生成一个jwt
        LoginUser loginUser=(LoginUser) authenticate.getPrincipal();
        String userid=loginUser.getUser().getId().toString();
        List<String> permission=userMapper.SearchPermissionByUserId(userid)
                .stream()
                .distinct()
                .filter(perm->!perm.isEmpty())
                .map(perm-> AuthConstant.AUTHORITY_PREFIX+perm)
                .collect(Collectors.toList());
        //查询路劲和权限的对应关系
        List<PermissionPathMap> pathList=permissionMapper.getPermissionPath();
        //重新分组：路径：权限permission列表
        //反过来，权限：路径列表应该也是可以的，但是我们是通过路劲来对比的，所以第一种更加好
        Map<String,List<String>> permissionMap=pathList.stream()
                .collect(Collectors.groupingBy(PermissionPathMap::getPath
                        ,Collectors.mapping(PermissionPathMap::getPermissionName,Collectors.toList())));
        String jwt=jwtUtil.createToken(userid,jwtProperties.getTokenTTL(),permission.stream().distinct().collect(Collectors.toList()));
        //Map<String,String> map = new HashMap<>();
        //map.put("token",jwt);
        //把完整的token和用户信息存入redis
        redisCache.setCacheObject("login:"+userid,loginUser);
        redisTemplate.opsForHash().putAll(RedisConstant.PERMISSION_ROUTE,permissionMap);
        log.info("成功刷新-权限映射hash");
        return new LoginVO(jwt);
    }
}
