package com.example.vo.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampusVO {
    String campusId;
    String campusName;
    List<OfficeVo> offices = new ArrayList<>();
}
