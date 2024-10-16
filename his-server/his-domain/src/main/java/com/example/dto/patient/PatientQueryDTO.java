package com.example.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientQueryDTO {
    private String id;
    private String name;
    private Integer gender;
    private Integer age;
    private String address;
    private String identity;
    private String phone;
    private String admissionNumber;
    private String admissionType;
    private String admissionTime;
    private String bedId;
    private Integer bookType;
    private Integer isEmergency;
    private Integer isVip;
    private Integer isAcute;
    private Integer isInHospital;
    private Integer preAssignBed;
    private String illness;
    private String createTime;
    private String createBy;
    private String updateTime;
    private String updateBy;

}

