package com.example.controller;

import com.example.dto.login.LoginDTO;
import com.example.service.LoginService;
import com.example.vo.JsonVO;
import com.example.vo.login.LoginVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
public class LoginController {

    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    public JsonVO<LoginVO> login(LoginDTO loginDTO){
        return JsonVO.success(loginService.login(loginDTO));
    }

    @PostMapping("/logout")
    public JsonVO<String> logout(@RequestHeader("userId") String userId){
        return JsonVO.success(loginService.logout(userId));
    }

}
