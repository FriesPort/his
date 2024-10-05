package com.example.bed.vo.bedAssign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedVo {
    Long bedId;//床位ID，beds表
    String roomNumber;//房间编号，“101房”，rooms表
    String bedNumber;//床位编号，“01床”，beds表
    String bedType;//床位类型，“正床”，beds表
    String roomType;//房间类型，“常规病房”，rooms表
    String roomGender;//病房性别
    Integer bedCount;//床位数量，6，rooms表
    String bedStatus;//床位状态，“空闲”，beds表
}
