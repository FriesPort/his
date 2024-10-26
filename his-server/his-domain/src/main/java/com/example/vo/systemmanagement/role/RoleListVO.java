package com.example.vo.systemmanagement.role;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色列表")
public class RoleListVO {
    private String id;

    private String name;

    private String description;
}
