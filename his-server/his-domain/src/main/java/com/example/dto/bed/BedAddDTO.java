package com.example.dto.bed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: his
 * @Author: HBTTAY
 * @Date: 2024/5/2 23:26
 * @Version: 1.0-SNAPSHOT
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedAddDTO {
    private String patientInformationId;//病人id
    private String roomId;//病房id
    private String bednumber;//病床号
    private String bedType;//病床类型
    private Integer bedStatus;//病床状态
}
