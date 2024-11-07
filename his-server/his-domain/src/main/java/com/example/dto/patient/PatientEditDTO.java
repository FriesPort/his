package com.example.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @ProjectName: hospital_
 * @Author:
 * @Date: 2024/10/04 14:09
 * @Version: 1.0-SNAPSHOT
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientEditDTO {

    private String id;                    //id
    private Map<String,String> patient;

}

