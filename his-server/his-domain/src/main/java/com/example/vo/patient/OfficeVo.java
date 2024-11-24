package com.example.vo.patient;

import com.example.vo.systemmanagement.ward.WardVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: hospital_xw
 * @Author: HBTTAY
 * @Date: 2024/5/6 16:39
 * @Version: 1.0-SNAPSHOT
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeVo {
    String officeId;
    String officeName;
    List<WardVo> wards = new ArrayList<>();
}
