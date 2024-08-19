package com.example.bed.vo.bedAssign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientVo {
    String patientName;//患者姓名
    String patientGender;//患者性别
    String waitDay;//等待时间
    String admissionType;//入院类型
    String bookType;//预约类型
    String telephoneNumber;//患者手机
    String roomNumberRequirement;//患者房间数量需求
    String roomGenderRequirement;//患者房间性别需求
    String roomTypeRequirement;//患者房间类型需求
    String patientStatus;//患者状态
    String isEmergency;//是否急诊
}
