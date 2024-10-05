package com.example.dto.systemmanagement.userrole;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户角色修改")
public class UserRoleUpdateDTO {
    private String token;
    private String user_id;
    private String role_id;
    private String update_role;
}
