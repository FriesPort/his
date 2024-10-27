package com.example.bed.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("room")
public class Room {
    @TableId(value="id" ,type = IdType.INPUT)
    private String id;//主键
    @TableField("number")
    private String number;//房号
    @TableField("type")
    private String type;//房间类型
    @TableField("gender")
    private String gender;//房间性别
    @TableField("bed_count")
    private Integer bedCount;//病床数量
    @TableField("foreign_id")
    private String foreignId;//外键
    @TableField("create_time")
    private LocalDateTime createTime;//创建时间
    @TableField("create_by")
    private String createBy;//创建人
    @TableField("update_time")
    private LocalDateTime updateTime;//修改时间
    @TableField("update_by")
    private String updateBy;//修改人
}