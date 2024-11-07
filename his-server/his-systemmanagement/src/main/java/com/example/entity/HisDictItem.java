package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 数据字典子项表
 * </p>
 *
 * @author diandian
 * @since 2024-11-07
 */
@Getter
@Setter
@TableName("his_dict_item")
public class HisDictItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 主表id
     */
    private String dictId;

    /**
     * 子项文本
     */
    private String itemText;

    /**
     * 子项值
     */
    private String itemValue;

    /**
     * 子项描述
     */
    private String description;

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
