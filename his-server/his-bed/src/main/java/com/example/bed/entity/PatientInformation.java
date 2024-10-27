package com.example.bed.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@TableName("patient_information")
public class PatientInformation {
    @TableId(value="id" ,type = IdType.INPUT)
    private String id;//患者id
    @TableField("name")
    private String patientName;//患者姓名
    @TableField("gender")
    private String patientGender;//患者性别
    @TableField("age")
    private Integer patientAge;//患者年龄
    @TableField("address")
    private String patientAddress;//患者地址
    @TableField("identity")
    private String identityCard;//患者身份证号
    @TableField("phone")
    private String telephoneNumber;//患者手机号
    @TableField("admission_number")
    private String admissionNumber;//入院号
    @TableField("admission_type")
    private String admissionType;//入院类型
    @TableField("admission_time")
    private LocalDateTime admissionTime;//入院时间
    @TableField("discharge_time")
    private LocalDateTime dischargeTime;//出院时间
    @TableField("bed_id")
    private String bedId;//床位号
    @TableField("book_type")
    private String bookType;//预约类型
    @TableField("is_emergency")
    private Integer isEmergency;//是否急诊（0否，1是）
    @TableField("is_vip")
    private Integer isVip;//是否VIP（0否，1是）
    @TableField("is_acute")
    private Integer isAcute;//是否重症（0否，1是）
    @TableField("is_inHospital")
    private Integer isHospitalized;//是否住院（0否，1是）
    @TableField("preAssignBed")
    private String preAssignBed;//是否预分配分配床位（0否，1是）
    @TableField("illness")
    private String illness;//疾病类型
    @TableField("create_time")
    private LocalDateTime createTime;//创建时间
    @TableField("create_by")
    private String createBy;//创建人
    @TableField("update_time")
    private LocalDateTime updateTime;//修改时间
    @TableField("update_by")
    private String updateBy;//修改人
}

