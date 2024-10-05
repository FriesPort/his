package org.example.controller;

import com.example.vo.JsonVO;
import com.example.vo.login.LoginVO;
import org.example.client.SystemClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    SystemClient systemClient;
    @PostMapping("/login")
    public JsonVO<LoginVO> login(){

        return null;
    }
}
