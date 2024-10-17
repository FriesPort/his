package com.example.vo.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: his
 * @Author: HBTTAY
 * @Date: 2024/5/2 23:00
 * @Version: 1.0-SNAPSHOT
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private boolean status = false;
    private T message;
}
