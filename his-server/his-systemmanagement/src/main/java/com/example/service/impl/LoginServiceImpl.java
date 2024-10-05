package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.config.JwtProperties;
import com.example.dto.login.LoginDTO;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.LoginService;
import com.example.utils.JwtTool;
import com.example.vo.login.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTool jwtTool;

    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserMapper userMapper;
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        String username= loginDTO.getUsername();
        String password= loginDTO.getPassword();
        User user = userMapper
                .selectOne(new QueryWrapper<User>()
                        .eq("username",username));
        if (user == null) {
            return new LoginVO("账号或密码错误",null,null);
        }
        if(user.getEnable()!=1){
            return new LoginVO("用户禁止使用",null,null);
        }
        if(!passwordEncoder.matches(password, user.getPassword())){
            return new LoginVO("账号或密码错误",null,null);
        }
        String token=jwtTool.createToken(user.getUserId(),jwtProperties.getTokenTTL());
        return new LoginVO("login successful",token, user.getUserId());
    }
}
