package com.example.vo.systemmanagement.permissions;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("权限列表")
public class PermissionListVO {
    private String id;

    private String name;

    private String description;
}
