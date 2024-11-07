package com.example.dto.systemmanagement.dict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HisDictItemAddDTO {
    /**
     * 主表id
     */
    private String dictId;

    /**
     * 子项文本
     */
    private String itemText;

    /**
     * 子项值
     */
    private String itemValue;

    /**
     * 子项描述
     */
    private String description;
}
