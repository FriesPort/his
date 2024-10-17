package com.example.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Patient information.
 */
@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
@TableName("patient_information")
public class patientInformation implements Serializable {
    private String id;    //id
    private String name;  //姓名
    private short gender;  //性别
    private int age;  //年龄
    private String address;
    private String identity;   //身份证号
    private String phone;
    private String admission_number;  //入院号
    private String admission_type;  //入院类型
    private String admission_time;  //入院时间
    private String discharge_time;  //出院时间
    private String bed_id;   //床位id-外键
    private short book_type;  //0代表线上预约，1代表线下预约
    private short is_emergency;  //默认为0，表示是否急症(0否，1是)
    private short is_vip;      //默认为0，表示是否为vip(0否，1是)
    private short is_acute;    //默认为0，表示是否重症(0否，1是)
//    @TableField("is_inhospital")
    private short is_inhospital;  //默认为0，是否在住院(0否，1是)
    private short preassignbed;  //预分配床位号   mybatisplus使用大写来匹配下划线
    private String illness;     //疾病类型
    private LocalDateTime create_time;
    private String create_by;  //创建人
    private LocalDateTime update_time;
    private String update_by;    //更新人

}
