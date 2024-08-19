package com.example.vo.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientVO {
    //患者id
    private Long patientId;
    //院区名字
    private String campusName;
    //科室名字
    private String officeName;
    //病区名字
    private String wardName;
    //患者姓名
    private String name;
    //患者性别
    private String Gender;
    //病房人数要求
    private String roomNumberRequirement;
    //病房性别要求
    private String roomGenderRequirement;
    //病房类型要求
    private String roomTypeRequirement;
    //电话号码
    private String phone;
    //等待时间
    private Long waitDay;
    //登记时间
    private LocalDateTime createTime;
    //是否为vip
    private Integer vip;
    //患者状态
    private String status;

    //学术价值
    private Integer academicValue;

    //经济效应
    private Integer economicImpact;
    //入院时间
    private LocalDateTime admissionTime;
    //出院时间
    private LocalDateTime dischargeTime;
}
