package com.example.dto.systemmanagement.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户删除")
public class UserDeleteDTO {
    private String user_id;
}
