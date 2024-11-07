package com.example.entity;

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
 * @since 2024-11-06
 */
@Getter
@Setter
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 医院名称
     */
    private String name;

    /**
     * 医院所在地区
     */
    private String region;

    /**
     * 医院标志
     */
    private String logo;

    /**
     * 配额
     */
    private Integer quota;

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
