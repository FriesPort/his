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
@TableName("bedlog")
public class Bedlog {
    @TableId(value="id" ,type = IdType.INPUT)
    private String id;
    @TableField("bed_id")
    private String bedId;
    @TableField("patient_information_id")
    private String patientInfomationId;
    @TableField("is_using")
    private Integer usingFlag;
    @TableField("create_time")
    private LocalDateTime createTime;//创建时间
    @TableField("create_by")
    private String createBy;//创建人
    @TableField("update_time")
    private LocalDateTime updateTime;//修改时间
    @TableField("update_by")
    private String updateBy;//修改人
}
