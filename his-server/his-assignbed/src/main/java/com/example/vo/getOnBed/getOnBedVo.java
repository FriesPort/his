package com.example.vo.getOnBed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The type Get on bed vo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class getOnBedVo {
    //床位号
    private String bedNumber;
    //床位种类
    private String bedType;
    //患者姓名
    private String patientName;
    //患者性别
    private short patientGender;
    //患者年龄
    private int patientAge;
    //房间号
    private String roomNumber;
    //房间种类
    private String roomType;
    //房间内的床位数量
    private Long bedCount;
    //房间性别
    private String roomGender;
}
