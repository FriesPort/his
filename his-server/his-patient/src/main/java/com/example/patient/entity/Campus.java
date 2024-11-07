package com.example.patient.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.sql.Blob;
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
public class Campus implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自动递增的主键
     */
    private String id;

    /**
     * 关联医院编号-外键
     */
    private String hospitalId;

    /**
     * 院区名称
     */
    private String name;

    /**
     * 二进制大对象
     */
    private Blob mapPackage;

    /**
     * 二进制大对象
     */
    private Blob themePackage;

    /**
     * 布尔类型授权状态
     */
    private Boolean isAuthorization;

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
