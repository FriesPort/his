package com.example.bed.entity;


import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("bed")
public class Bed {
    @TableId(value="id" ,type = IdType.INPUT)
    private String bedId;//病床id
    @TableField("number")
    private String number;//病床编号
    @TableField("patient_id")
    private String patientInformationId;//病人id
    @TableField("room_id")
    private String roomId;//病房id
    @TableField("type")
    private String bedType;//病床类型
    @TableField("bednumber")
    private String bednumber;//病床号码-------------------新增
    @TableField("is_null")
    private Integer bedStatus;//病床状态
    @TableField("create_time")
    private LocalDateTime createTime;//创建时间
    @TableField("create_by")
    private String createBy;//创建人
    @TableField("update_time")
    private LocalDateTime updateTime;//修改时间
    @TableField("update_by")
    private String updateBy;//修改人
}
