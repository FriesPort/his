package com.example.vo.systemmanagement.userrole;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("权限删除功能的返回信息")
public class UserRoleDeleteVO {
    public static String success="Permission has been deleted.";
    public static String fail="Failed to delete permission.";
}
