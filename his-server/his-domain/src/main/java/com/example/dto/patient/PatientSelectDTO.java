package com.example.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientSelectDTO {
    //分页
    @NotNull
    private Integer pageNum;
    @NotNull
    private Integer pageSize;
    //院区id

    private Long campusId;
    //科室id

    private Long officeId;
    //病区id

    private Long wardId;
    //姓名
    private String name;
    //性别
    private String gender;
    //入院类型
    private String admissionType;
    //预约方式
    private String bookType;
    //病房人数要求
    private String roomNumberRequirement;
    //病房性别要求
    private String roomGenderRequirement;
    //病房类型要求
    private String roomTypeRequirement;
    //等待时间
    private Long waitDay;
    //排序方式
    private String sortBy;
    //患者状态
    private String status;
}

