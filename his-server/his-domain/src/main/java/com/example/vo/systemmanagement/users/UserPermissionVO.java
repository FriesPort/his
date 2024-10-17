package com.example.vo.systemmanagement.users;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("自定义用户功能的返回信息")
public class UserPermissionVO {
    private List<String> permission;
}
