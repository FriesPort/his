package com.example.dto.systemmanagement.userrole;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户角色权限查询")
public class UserRolePermissionSearchDTO {
    private String user_id;

}
