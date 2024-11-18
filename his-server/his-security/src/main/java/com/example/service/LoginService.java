package com.example.service;

import com.example.dto.login.LoginDTO;
import com.example.vo.JsonVO;
import com.example.vo.login.LoginVO;

public interface LoginService {
    JsonVO<?> login(LoginDTO loginDTO);

    String logout(String userId,String token);
}
