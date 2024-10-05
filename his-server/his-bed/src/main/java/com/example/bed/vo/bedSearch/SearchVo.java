package com.example.bed.vo.bedSearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchVo {
    /**
     * 患者信息
     */
    Long patientId;
    String patientName;    //患者姓名
    String patientGender;    //患者性别
    Integer patientAge;    //患者年龄
    String telephoneNumber;    //患者手机
    String admissionNumber;    //入院号
    String admissionType;    //入院类型
    String admissionTime;    //入院时间
    String bookType;    //预约类型
    Integer isEmergency;    //是否急诊
    String doctor;    //医生
    String illness;    //疾病
    Integer isAcute;    //是否重症
    Integer isVip;    //是否VIP

    /**
     * 床位及其房间信息
     */
    Long bedId;    //床位ID，beds表
    String roomNumber;    //房间编号，“101房”，rooms表
    String bedNumber;    //床位编号，“01床”，rooms表
    String bedType;    //床位类型，“男床”，beds表
    String roomType;    //房间类型，“常规病房”，rooms表
    String roomGender;  //房间类型，“混合病房”，rooms表
    Integer bedCount;    //床位数量，6，rooms表
    String bedStatus;    //床位状态，“空闲”，beds表
}
