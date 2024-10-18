package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The type Bed.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("bed")
public class bed {
    private String id;
    private String patient_id;
    private String room_id;
    private String type;    //床位类型
    private String number;  //床位编号
    private short is_null;  //是否为空床位 0为空，1为占用 默认为0
    private LocalDateTime create_time;
    private String create_by;   //创建人
    private LocalDateTime update_time;
    private String update_by;   //更新人
}
