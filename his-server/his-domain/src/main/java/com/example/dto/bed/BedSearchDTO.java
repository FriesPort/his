package com.example.dto.bed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedSearchDTO {
    //检索区域，病区-->科室-->院区
    private String campusId;//院区
    private String officeId;//科室
    private String wardId;//病区
    private  String bedType;//床位类型
    private Integer bedStatus;//床位状态
    private String roomType;//病房类型
    private String roomGender;//病房性别
}

