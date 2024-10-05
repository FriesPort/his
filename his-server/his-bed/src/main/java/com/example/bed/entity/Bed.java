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
 * @since 2024-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("beds")
public class Bed implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "bed_id", type = IdType.AUTO)
    private Long bedId;

    private Long patientId;

    private Long roomId;

    private String bedType;

    private String bedNumber;

    @TableField("status")
    private String bedStatus;


}
