package com.example.patient.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("patient_hospitalization_record") // 表名改为与实际表名匹配
public class Patient_recode implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;  // 主键

    @TableField("patient_id")
    private String patientId;  // 患者 id (外键)

    @TableField("bed_id")
    private String bedId;  // 床位 id (外键)

    @TableField("admission_time")
    private LocalDateTime admissionTime;  // 入院时间

    @TableField("discharge_time")
    private LocalDateTime dischargeTime;  // 出院时间

    @TableField("admission_diagnosis")
    private String admissionDiagnosis;  // 入院诊断

    @TableField("discharge_diagnosis")
    private String dischargeDiagnosis;  // 出院诊断

    @TableField("symptoms")
    private String symptoms;  // 主要症状

    @TableField("treatments")
    private String treatments;  // 医疗过程描述

    @TableField("total_cost")
    private BigDecimal totalCost;  // 住院总费用

    @TableField("doctor_id")
    private String doctorId;  // 医生 id (外键)

    @TableField("nurse_id")
    private String nurseId;  // 护士 id (外键)
}

