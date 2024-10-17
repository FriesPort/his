package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author diandian
 * @since 2024-10-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 唯一键，员工编号
     */
    private String employeeNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户姓名
     */
    private String employeeName;

    /**
     * 关联医院编号-外键
     */
    private String hospitalId;

    /**
     * 院区编号-外键
     */
    private String campusId;

    /**
     * 用户类型（医生、护士）
     */
    private String userType;

    /**
     * 是否可用，是否可用的字段可以改为tinyint,，只有0和1的状态，节省内存空间
     */
    private Integer isUse;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime=LocalDateTime.now();

    /**
     * 更新人
     */
    private String updateBy;


}
