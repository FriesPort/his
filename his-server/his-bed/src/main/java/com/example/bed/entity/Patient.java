package com.example.bed.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2024-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("patient_information")
public class Patient implements Serializable {

    @TableField(exist = false)
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private static final long serialVersionUID = 1L;

    @TableId(value = "patient_id", type = IdType.AUTO)
    private Long patientId;//患者id
    @TableField("name")
    private String patientName;//患者姓名
    @TableField("gender")
    private String patientGender;//患者性别
    @TableField("age")
    private Integer patientAge;
    private String address;
    @TableField("phone")
    private String telephoneNumber;//患者手机号
    private String admissionType;//入院类型
    private String admissionNumber;//入院号
    private LocalDateTime admissionTime;//入院时间
    private LocalDateTime dischargeTime;//出院时间
    private String bookType;//预约类型
    private Integer isEmergency;//是否急诊（0否，1是）
    private Integer isAcute;
    private Integer isVip;
    private Integer economicImpact;//经济效益
    private Integer academicValue;//学术价值
    private String illness;//疾病类型
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer roomNumberRequirement;//患者房间人数需求
    private String roomTypeRequirement;//患者房间类型需求
    private String roomGenderRequirement;
    private Long wardId;
    private Long campusId;
    private Long officeId;
    private String status;//患者状态（“待入园”“已入院”“已出院”）
    private String doctor;//主治医生
    private String nurse;//责任护士
    private Integer del;
}
