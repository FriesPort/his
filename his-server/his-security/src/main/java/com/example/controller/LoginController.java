package com.example.controller;

import com.example.dto.login.LoginDTO;
import com.example.service.LoginService;
import com.example.vo.JsonVO;
import com.example.vo.login.LoginVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class LoginController {

    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    public JsonVO<LoginVO> login(@RequestBody LoginDTO loginDTO){
        return JsonVO.success(loginService.login(loginDTO));
    }
}
