package com.example.dto.systemmanagement.user;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("添加用户。用于用户注册")
public class UserAddDTO {

    private String username;

    private String password;

    private String name;

    private String hospitalId="H789";

    private String campusId;

    private String userType;

    private String Enable;
    private String phone;

}
