package com.example.bed.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("all_id")
public class AllId {
    @TableId(value="id" ,type = IdType.INPUT)
    private String id;//id
    @TableField("campus_id")
    private String campusId;//院区id
    @TableField("floor_id")
    private String officeId;//科室id
    @TableField("ward_id")
    private String wardId;//病区id
    @TableField("hosiptal")
    private String hospital;//医院
    @TableField("create_time")
    private LocalDateTime createTime;//创建时间
    @TableField("create_by")
    private String createBy;//创建人
    @TableField("update_time")
    private LocalDateTime updateTime;//修改时间
    @TableField("update_by")
    private String updateBy;//修改人

}
