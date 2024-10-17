package com.example.service;

import com.example.dto.login.LoginDTO;
import com.example.vo.login.LoginVO;

public interface LoginService {
    LoginVO login(LoginDTO loginDTO);

}
