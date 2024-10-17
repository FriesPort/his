package com.example.patient.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author xw
 * @since 2024-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("patient_information")
public class QueryPatient implements Serializable {

    @TableField(exist = false)
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @TableId(value = "id", type = IdType.INPUT) // 改为与数据库表字段匹配
    private String id;  // 患者 id

    private String name;  // 患者姓名

    private Integer gender;  // 患者性别（0 表示女，1 表示男）

    private Integer age;  // 患者年龄

    private String address;  // 患者地址

    private String identity;  // 身份证号

    private String phone;  // 患者手机号

    @TableField("admission_number") // 映射数据库中的 admission_number
    private String admissionnumber;  // 入院号

    @TableField("admission_type") // 映射数据库中的 admission_type
    private String admissiontype;  // 入院类型

    @TableField("admission_time") // 映射数据库中的 admission_time
    private LocalDateTime admissiontime;  // 入院时间

    @TableField("discharge_time") // 映射数据库中的 discharge_time
    private LocalDateTime dischargeTime;  // 出院时间

    @TableField("bed_id") // 映射数据库中的 bed_id
    private String bedId;  // 床位 id（外键）

    @TableField("book_type") // 映射数据库中的 book_type
    private Integer booktype;  // 预约类型（0 代表线上，1 代表线下）

    @TableField("is_emergency") // 映射数据库中的 is_emergency
    private Integer isemergency;  // 是否急诊（0 否，1 是）

    @TableField("is_vip") // 映射数据库中的 is_vip
    private Integer isvip;  // 是否 VIP（0 否，1 是）

    @TableField("is_acute") // 映射数据库中的 is_acute
    private Integer isacute;  // 是否重症（0 否，1 是）

    @TableField("is_inhospital") // 映射数据库中的 is_inHospital
    private Integer isInhospital;  // 是否在住院（0 否，1 是）

    @TableField("preassignBed") // 映射数据库中的 preAssignBed
    private String preassignbed;  // 预分配床位号，0 表示未预分配

    private String illness;  // 疾病类型

    @TableField("create_time") // 映射数据库中的 create_time
    private LocalDateTime createTime;  // 创建时间

    @TableField("create_by") // 映射数据库中的 create_by
    private String createby;  // 创建人

    @TableField("update_time") // 映射数据库中的 update_time
    private LocalDateTime updateTime;  // 更新时间

    @TableField("update_by") // 映射数据库中的 update_by
    private String updateBy;  // 更新人
}
