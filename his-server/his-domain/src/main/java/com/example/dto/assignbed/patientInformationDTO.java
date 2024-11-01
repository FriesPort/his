package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Patient information dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class patientInformationDTO {
    //患者id
    private String id;
    //更新人
    private String name;
    //预分配的床位   预分配才用到
    private String bedId;
}
