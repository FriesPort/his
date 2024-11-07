package com.example.dto.systemmanagement.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalUpdateDTO {
    private String id;
    private Map<String ,String> hospital;
}
