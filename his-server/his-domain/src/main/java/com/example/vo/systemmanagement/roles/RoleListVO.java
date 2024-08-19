package com.example.vo.systemmanagement.roles;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色列表")
public class RoleListVO {
    private String roleId;

    private String roleName;

    private String description;
}
