package com.example.bed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedSearchDTO {
    //检索区域，病区-->科室-->院区
    Integer wardId;
    Integer officeId;
    Integer campusId;
    //床位类型
    String bedType;
    //病房类型
    String roomType;
    //病房人数
    Integer bedCount;
    //病房性别
    String roomGender;
}
