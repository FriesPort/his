package com.example.dto.assignbed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Patient change bed dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class patientChangeBedDTO {
    //患者1id
    private String patientId1;
    //患者2id
    private String patientId2;
    //更新人
    private String name;
    //更新时间
    private String localDateTime;
}
