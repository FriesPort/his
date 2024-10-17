package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String username;

    private String employeeNumber;

    private String password;

    private String name;

    private String hospitalId;

    private String campusId;

    private String userType;

    private String status="active";

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField("Enable")
    private int Enable=1;

    private String phone;


}
