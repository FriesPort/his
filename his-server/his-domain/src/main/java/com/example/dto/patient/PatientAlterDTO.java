package com.example.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ProjectName: hospital_
 * @Author:
 * @Date: 2024/10/04 14:09
 * @Version: 1.0-SNAPSHOT
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAlterDTO {


    private String name;                  // 患者姓名
    private Integer gender;               // 患者性别（0 女, 1 男）
    private Integer age;                  // 患者年龄
    private String address;               // 患者地址
    private String identity;              // 身份证号
    private String phone;                 // 患者手机号
    private String admissionnumber;       // 入院号
    private String admissiontype;         // 入院类型
    private String admissiontime;         // 入院时间（保持为 String，若需要精确时间处理可考虑 LocalDateTime）
    private Integer booktype;             // 预约类型（0 线上, 1 线下）
    private Integer isemergency;          // 是否急诊（0 否, 1 是）
    private Integer isvip;                // 是否 VIP（0 否, 1 是）
    private Integer isacute;              // 是否重症
    private String illness;               // 疾病类型
    private String createby;              // 创建人

}

