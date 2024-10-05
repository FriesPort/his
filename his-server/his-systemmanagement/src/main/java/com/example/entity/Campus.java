package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Blob;

/**
 * <p>
 * 
 * </p>
 *
 * @author diandian
 * @since 2024-05-16
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
