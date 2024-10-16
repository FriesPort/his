package com.example.dto.systemmanagement.users;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("添加用户。用于用户注册")
public class UsersAddDTO {

    private String username;

    private String password;

    private String name;

    private String hospitalId="H789";

    private String campusId;

    private String userType;

    private String Enable;
    private String phone;

}
