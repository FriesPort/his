package com.example.vo.bed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedVo {
    String roomNumber;
    String bedId;//床位id
    String bedNumber;//床位编号
    String bedType;//床位类型
    Integer bedStatus;//床位状态
    String roomType;//病房类型
    String roomGender;//病房性别
    String roomId;//病房id
    Object patient;
}