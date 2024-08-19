package com.example.vo.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(description = "分页结果")
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {
    @ApiModelProperty("总条数")
    private Long total;
    @ApiModelProperty("集合")
    private List<T> items;

}