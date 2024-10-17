package com.example.dto.systemmanagement.users;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户信息展示")
public class UserDisplayDTO {
    private String name;
    private String campusId;
    private String employeeNumber;
}
