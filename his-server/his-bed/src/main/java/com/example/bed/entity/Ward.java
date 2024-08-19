package com.example.bed.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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

    @TableId(value = "ward_id", type = IdType.AUTO)
    private Long wardId;

    @TableField("name")
    private String wardName;

    private String code;

    private Long officeId;

    private Integer roomCount;

    private Integer bedCount;
}
