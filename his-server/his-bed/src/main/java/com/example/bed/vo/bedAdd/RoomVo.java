package com.example.bed.vo.bedAdd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: hospital_xw
 * @Author: HBTTAY
 * @Date: 2024/5/7 16:40
 * @Version: 1.0-SNAPSHOT
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomVo {
    Long roomId;
    String roomNumber;
    String roomGender;
    Integer bedCount;
}
