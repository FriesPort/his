package com.zeroone.star.his-systemmanagement.entity;

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
 * @since 2024-10-17
 */
@Getter
@Setter
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    private String name;

    /**
     * 权限名称
     */
    private String description;

    /**
     * 是否启用数据权限，0为否，1为是，默认为0
     */
    private Integer isDatarule;

    /**
     * 权限是否可用，0为否，1为是，默认为1
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
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 权限对应的功能的路由
     */
    private String route;


}
