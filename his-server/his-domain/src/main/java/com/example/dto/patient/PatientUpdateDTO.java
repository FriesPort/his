package com.example.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateDTO {
    //病人id
    @NotNull
    private Long patientId;
    //院区id
    private Long campusId;
    //科室id
    private Long officeId;
    //病区id
    private Long wardId;
    //姓名
    private String name;
    //性别
    private String gender;
    //年龄
    private Integer age;
    //地址
    private String address;
    //电话号码
    private String phone;
    //入院类型
    private String admissionType;
    //预约方式
    private String bookType;
    //病房人数要求
    private String roomNumberRequirement;
    //病房性别要求
    private String roomGenderRequirement;
    //病房类型要求
    private String roomTypeRequirement;
    //主治医生
    private String doctor;
    //责任护士
    private String nurse;
    //患者状态
    private String status;
    //所患疾病
    private String illness;
    //是否为vip
    private Integer isVip;
    //是否紧急
    private Integer isEmergency;
    //学术价值
    private Integer academicValue;
    //经济效应
    private Integer economicImpact;
}
