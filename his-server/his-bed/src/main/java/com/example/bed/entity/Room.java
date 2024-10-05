package com.example.bed.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xw
 * @since 2024-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rooms")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "room_id", type = IdType.AUTO)
    private Long roomId;

    /**
     * 病房编号
     */
    private String roomNumber;

    /**
     * 病房类型
     */
    private String roomType;

    /**
     * 病房性别
     */
    private String roomGender;

    /**
     * 床位数量
     */
    private Integer bedCount;

    /**
     * 病区id
     */
    private Long wardId;

    /**
     * 科室id
     */
    private Long officeId;

    /**
     * 院区id
     */
    private Long campusId;

    /**
     * 楼层id
     */
    private Long floorsId;


}
