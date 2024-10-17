package com.example.vo.patient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PatientVo{

    private String id;  // 患者 id
    private String name;  // 患者姓名
    private Integer gender;  // 患者性别（0 表示女，1 表示男）
    private Integer age;  // 患者年龄
    private String address;  // 患者地址
    private String identity;  // 身份证号
    private String phone;  // 患者手机号
    private String admissionnumber;  // 入院号
    private String admissiontype;  // 入院类型
    private String admissiontime;  // 入院时间
    private String dischargeTime;  // 出院时间
    private String bedId;  // 床位 id（外键）
    private Integer booktype;  // 预约类型（0 代表线上，1 代表线下）
    private Integer isemergency;  // 是否急诊（0 否，1 是）
    private Integer isvip;  // 是否 VIP（0 否，1 是）
    private Integer isacute;  // 是否重症（0 否，1 是）
    private Integer isInhospital;  // 是否在住院（0 否，1 是）
    private String preassignbed;  // 预分配床位号，0 表示未预分配
    private String illness;  // 疾病类型
    private String createTime;  // 创建时间
    private String createby;  // 创建人
    private String updateTime;  // 更新时间
    private String updateBy;  // 更新人
    private Integer waitDay;
}
