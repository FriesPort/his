package com.example.dto.systemmanagement.campus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampusUpdateDTO {
    private String id;
    private Map<String ,String> campus;
}
