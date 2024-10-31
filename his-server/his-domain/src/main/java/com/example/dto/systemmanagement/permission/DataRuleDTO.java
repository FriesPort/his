package com.example.dto.systemmanagement.permission;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据权限注册")
public class DataRuleDTO {
    private String permissionId;
    private String ruleColumn;
    private String ruleConditions;
    private String ruleValue;
    private String ruleName;
}
