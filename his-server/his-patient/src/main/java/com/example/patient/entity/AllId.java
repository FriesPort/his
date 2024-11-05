package com.example.patient.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author diandian
 * @since 2024-11-05
 */
@Getter
@Setter
@TableName("all_id")
public class AllId implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自动递增的主键
     */
    private String id;

    /**
     * 患者编号
     */
    private String patientId;

    /**
     * 院区编号
     */
    private String campusId;

    /**
     * 科室编号
     */
    private String officeId;

    /**
     * 病区编号
     */
    private String wardId;

    /**
     * 医院名称
     */
    private String hospital;

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
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateBy;


}
