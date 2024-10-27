package com.example.bed.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Blob;

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
@TableName("campus")
public class Campus implements Serializable {

    @TableId(value="id" ,type = IdType.INPUT)
    private String campusId;

    private String hospitalId;

    @TableField("name")
    private String campusName;

    private Blob mapPackage;

    private Blob themePackage;
    @TableField("is_authorization")
    private Boolean authorizationStatus;
}
