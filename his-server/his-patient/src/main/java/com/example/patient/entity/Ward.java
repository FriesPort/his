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
public class Ward implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自动递增的主键
     */
    private String id;

    /**
     * 病区名称
     */
    private String name;

    /**
     * 代码
     */
    private String code;

    /**
     * 关联科室编号
     */
    private String officeId;

    /**
     * 房间数量
     */
    private Integer roomCount;

    /**
     * 床位数量
     */
    private Integer bedCount;

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
     * 病区位置
     */
    private String location;


}
