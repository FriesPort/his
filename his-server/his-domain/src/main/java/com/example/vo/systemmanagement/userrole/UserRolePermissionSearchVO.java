package com.example.vo.systemmanagement.userrole;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户角色权限查询功能的返回信息")
public class UserRolePermissionSearchVO<T> {
    private String roleid;
    private String rolename;
    private List<T> permission;

}
