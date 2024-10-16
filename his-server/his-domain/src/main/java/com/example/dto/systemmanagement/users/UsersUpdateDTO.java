package com.example.dto.systemmanagement.users;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户信息编辑")
public class UsersUpdateDTO {
    private String user_id;
    Map<String,Object> key_data;
}
