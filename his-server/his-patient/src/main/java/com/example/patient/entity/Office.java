package com.example.patient.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Office implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自动递增的主键
     */
    private String id;

    /**
     * 科室名称
     */
    private String name;

    /**
     * 关联院区编号-外键
     */
    private String campusId;

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

    /**
     * 备注
     */
    private String remark;

    /**
     * 科室电话
     */
    private String phone;


}
