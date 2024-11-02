package com.example.vo.systemmanagement.ward;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName:wardVo
 * Package:com.example.vo.systemmanagement.ward
 * Description:
 *
 * @Author: 绯雾sama
 * @Create:2024/11/2 12:16
 * Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardVo implements Serializable {
    private String id;
    private String name;
}
