package com.example.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientQueryDTO {
    Integer campusId;//院区id
    Integer officeId;//科室id
    Integer wardId;//病区id
    String name;//姓名
    String gender;//性别
    String admissionType;//入院类型
    String bookType;//预约方式
    String roomNumberRequirement;//病房人数要求
    String roomGenderRequirement;//病房性别要求
    String roomTypeRequirement;//病房类型要求
    Integer waitDay;//等待天数
    String status;//患者状态
}

