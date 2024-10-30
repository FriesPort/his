package com.example.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDeleteDTO {
    private String id;
    private String dischargeDiagnosis;  // 出院诊断
    private String treatments;  // 医疗过程描述

}
