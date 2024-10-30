package com.example.dto.systemmanagement.permission;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("权限注册")
public class PermissionRegisterDTO {
    // 权限名称
    private String name;
    private String description;
    private Integer isDatarule;
    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 字段
     */
    private String ruleColumn;

    /**
     * 条件
     */
    private String ruleConditions;

    /**
     * 规则值
     */
    private String ruleValue;

}
