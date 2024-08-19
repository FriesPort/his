package com.example.dto.systemmanagement.userrole;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户角色删除")
public class UserRoleDeleteDTO {
    private String user_id;
    private List<String> delete_role;
}
