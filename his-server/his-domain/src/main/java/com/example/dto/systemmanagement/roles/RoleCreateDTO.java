package com.example.dto.systemmanagement.roles;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("自定义角色的权限信息")
public class RoleCreateDTO {
    private String roleName;
    private String description;
    private List<String> permissions;
}
