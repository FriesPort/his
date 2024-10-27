package com.example.patient.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("room_user") // 表名改为与实际表名匹配
public class Roomuser implements Serializable {
    @TableField("room_id")
    private String roomid;  // 患者 id (外键)

    @TableField("user_id")
    private String doctorId;  // 医生 id (外键)

    @TableField("remark")
    private String nurseId; // 护士 id (外键)
}

