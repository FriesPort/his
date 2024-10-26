package com.example.vo.systemmanagement.roles;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("自定义角色功能的返回信息")
public class RoleCreateVO<T> {
    private String roleId;

    private String roleName;

    private String description;
    private List<T>  permissionsName;
}
