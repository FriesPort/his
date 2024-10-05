package com.example.vo.systemmanagement.userrole;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("权限修改功能的返回信息")
public class UserRoleUpdateVO {
    public static String success="Permission has been updated.";
    public static String fail="Failed to update permission.";
}
