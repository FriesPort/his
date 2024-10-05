package com.example.bed.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
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
@TableName("campus")
public class Campus implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "campus_id", type = IdType.AUTO)
    private Long campusId;

    private String hospitalId;

    @TableField("name")
    private String campusName;

    private Blob mapPackage;

    private Blob themePackage;

    private Boolean authorizationStatus;
}
