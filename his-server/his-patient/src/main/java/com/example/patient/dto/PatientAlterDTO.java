package com.example.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ProjectName: hospital_xw
 * @Author: HBTTAY
 * @Date: 2024/5/10 14:09
 * @Version: 1.0-SNAPSHOT
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAlterDTO {
    private Integer id;//患者id
    private String name;//患者姓名
    private String gender;//患者性别
    private Integer age;//患者年龄
    private String address;//患者地址
    private String phone;//患者手机号
    private String admissionType;//入院类型
    private String bookType;//预约类型
    private String illness;//疾病类型
    private Long wardId;//病区id
    private Long officeId;//科室id
    private Long campusId;//院区id
    private Integer roomNumberRequirement;//患者房间人数需求
    private String roomTypeRequirement;//患者房间类型需求
    private String roomGenderRequirement;//患者性别类型需求
    private String doctor;//主治医生
    private Integer isEmergency;//是否急诊（0否，1是）
    private Integer isVip;//是否vip（0否，1是）
    private Integer isAcute;//是否重症
}
