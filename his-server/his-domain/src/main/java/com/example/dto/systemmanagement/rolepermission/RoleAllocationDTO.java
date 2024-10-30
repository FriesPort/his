package com.example.dto.systemmanagement.rolepermission;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色权限分配")
public class RoleAllocationDTO {
    private String roleId;
    private List<String> add;
    private List<String> del;

}
