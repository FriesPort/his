package com.example.vo.systemmanagement.role;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色所拥有的权限展示")
public class PermissionDisplayVO {
    private String permissionName;
    private String description;
    private Map<String, String> data_rule;
}
