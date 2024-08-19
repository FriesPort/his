package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author diandian
 * @since 2024-04-17
 */
@Getter
@Setter
@TableName("user_roles")
public class UserRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String roleId;

    private String urId;


}
