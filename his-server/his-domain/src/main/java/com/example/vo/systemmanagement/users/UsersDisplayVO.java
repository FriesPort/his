package com.example.vo.systemmanagement.users;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户信息展示")
public class UsersDisplayVO {
    private String id;

    private String username;

    private String employeeName;

    private String employeeNumber;

    private String campusName;

    private String userType;

    private String status;

    private int isEnable;
    private String phone;


}
