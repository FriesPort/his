package com.example.dto.systemmanagement.permission;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("权限编辑")
public class PermissionUpdateDTO {
    String permissionId;
    Map<String,String> permission;
}
