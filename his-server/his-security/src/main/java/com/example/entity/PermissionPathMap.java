package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionPathMap {
    @TableField(value = "route")
    private String path;
    @TableField(value = "name")
    private String permissionName;
}
