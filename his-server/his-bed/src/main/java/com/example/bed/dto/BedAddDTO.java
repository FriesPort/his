package com.example.bed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: his
 * @Author: HBTTAY
 * @Date: 2024/5/2 23:26
 * @Version: 1.0-SNAPSHOT
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedAddDTO {
    private Long roomId;
    private String bedNumber;
    private String bedType;
}
