package com.example.bed.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xw
 * @since 2024-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ward")
public class Ward implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id" ,type = IdType.INPUT)
    private String wardId;

    @TableField("name")
    private String wardName;
    @TableField("code")
    private String code;
    @TableField("office_id")
    private String officeId;
    @TableField("room_count")
    private Integer roomCount;
    @TableField("bed_count")
    private Integer bedCount;
}
