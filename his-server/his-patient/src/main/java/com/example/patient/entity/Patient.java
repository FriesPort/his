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
public class Patient implements Serializable {
    @TableField(exist = false)
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @TableId(value = "patient_id", type = IdType.AUTO)
    private Long patientId;//患者id
    private String name;//患者姓名
    private String gender;//患者性别
    private Integer age;//患者年龄
    private String address;//患者地址
    private String phone;//患者手机号
    private String admissionNumber;//入院号
    private String admissionType;//入院类型
    private LocalDateTime admissionTime;//入院时间
    private LocalDateTime dischargeTime;//出院时间
    private String bookType;//预约类型
    private Integer isEmergency;//是否急诊（0否，1是）
    private Integer isVip;//是否vip（0否，1是）
    private Integer economicImpact;//经济效益
    private Integer academicValue;//学术价值
    private Integer isAcute;//是否重症
    private String illness;//疾病类型
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Integer roomNumberRequirement;//患者房间人数需求
    private String roomTypeRequirement;//患者房间类型需求
    private String roomGenderRequirement;//患者性别类型需求
    private Long wardId;//病区id
    private Long officeId;//科室id
    private Long campusId;//院区id
    private String doctor;//主治医生
    private String nurse;//责任护士
    private String status;//患者状态（“待入院”“已入院”“已出院”）
    private Integer del;//删除标记
}
