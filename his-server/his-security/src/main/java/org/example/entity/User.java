package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {
    @TableId
    private String id;
    private String username;
    private String employeeNumber;
    private String password;
    private String employeeName;
    private String hospitalId;
    private String campusId;
    private String userType;
    private Integer isUse;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
}
