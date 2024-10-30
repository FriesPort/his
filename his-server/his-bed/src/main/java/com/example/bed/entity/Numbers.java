package com.example.bed.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Numbers {
    private String campus;
    private String office;
    private String ward;
    private String floor;
    private String room;
    private String bednumber;
    private String bedType;
    private Integer bedStatus;
    private String roomType;//病房类型
    private String roomGender;//病房性别
    private String patientinformationId;//病人信息id
    private String roomId;//病房id
}

