package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The type Room.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class room {
    private String id;
    private String number;  //病房号
    private String type;   //病房种类
    private String gender;  //病房性别
    private Long bed_count;  //床位数量
    private String foreign_id; //ward_id-office_id-campus_id-floors_id
    private LocalDateTime create_time;
    private String create_by;
    private LocalDateTime update_time;
    private String update_by;
}
