package com.example.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientQueryDTO {
    private String campusId;
    private String wardId;
    private String name;
    private String gender;
    private String admissionType;
    private String roomNumberRequirement;
    private String roomGenderRequirement;
    private String roomTypeRequirement;
    private Integer waitDay;
    private String status;

}

