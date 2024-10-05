package com.example.patient.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientVo {
    //患者表自带信息
    private Long patientId;//患者id
    private String name;//患者姓名
    private String gender;//患者性别
    private Integer age;//患者年龄
    private String phone;//患者手机号
    private String createTime;//创建时间
    private String admissionTime;//入院时间
    private String dischargeTime;//出院时间
    private String admissionNumber;//入院号
    private String admissionType;//入院类型
    private String bookType;//预约类型
    private Integer isEmergency;//是否急诊（0否，1是）
    private Integer isVip;//是否vip（0否，1是）
    private Integer isAcute;//是否重症
    private String illness;//疾病类型
    private Integer roomNumberRequirement;//患者房间人数需求
    private String roomTypeRequirement;//患者房间类型需求
    private String roomGenderRequirement;//患者性别类型需求
    private String doctor;//主治医生
    private String status;//患者状态（“待入院”“已入院”“已出院”）
    //其他信息
    private Integer waitDay;//等待天数
    private String bedNumber;//床位编号
    private String roomNumber;//房间编号
    private String roomType;
    private String roomGender;
    private Long bedCount;


}