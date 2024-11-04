package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ClassName:Ward
 * Package:com.example.entity
 * Description:
 *
 * @Author: 绯雾sama
 * @Create:2024/10/31 16:51
 * Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ward")
public class Ward {

    private String id;

    private String name;

    private String code;

    private String officeId;

    private Integer roomCount;

    private Integer bedCount;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private String remark;

    private String location;
}
