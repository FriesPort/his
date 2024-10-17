package com.example.vo.getOutBed;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The type Get out bed vo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class getOutBedVo {
    //房间号
    private String roomNumber;
    //房间种类
    private String roomType;
    //房间内的床位数量
    private Long bedCount;
    //房间性别
    private String roomGender;
    //床位号
    private String bedNumber;
    //床位种类
    private String bedType;

}
