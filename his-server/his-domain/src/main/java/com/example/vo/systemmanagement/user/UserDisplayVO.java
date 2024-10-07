package com.example.vo.systemmanagement.user;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户信息展示")
public class UserDisplayVO {
    private String id;

    private String username;

    private String employeeName;

    private String employeeNumber;

    private String campusName;

    private String userType;

    private int isUse;
}
