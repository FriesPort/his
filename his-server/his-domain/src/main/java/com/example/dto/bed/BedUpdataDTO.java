package com.example.dto.bed;

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
public class BedUpdataDTO {
    //床位ID
    private String bedId;
    //患者ID
    private String patientInformationId;
}
