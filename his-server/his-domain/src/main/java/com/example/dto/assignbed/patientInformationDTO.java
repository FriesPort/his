package com.example.dto.assignbed;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Patient information dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class patientInformationDTO {
    //患者id
    private String id;
    //更新人
    @TableField("update_by")
    private String name;
    //预分配的床位   预分配才用到
    @TableField("bed_id")
    private String bedId;
    //更新时间
    @TableField("update_time")
    private String localDateTime;
}
