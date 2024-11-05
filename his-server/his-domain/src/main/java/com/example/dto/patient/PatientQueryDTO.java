package com.example.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientQueryDTO {
    /**
     * campusId
     * wardId
     * name
     * sex
     *
     */
    Map<String,String> query;

}

