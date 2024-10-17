package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String employeeNumber;

    private String password;

    private String employeeName;

    private String hospitalId;

    private String campusId;

    private String userType;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String createBy;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    private String updateBy;

    private int isUse;
}
