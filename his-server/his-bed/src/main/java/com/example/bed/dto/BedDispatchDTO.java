package com.example.bed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: his
 * @Author: HBTTAY
 * @Date: 2024/4/28 15:43
 * @Version: 1.0-SNAPSHOT
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedDispatchDTO {
    //床位ID
    Integer bedId;
    //患者ID
    Integer patientId;
}
