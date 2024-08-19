package com.example.patient.entity;

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
 * @since 2024-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rooms")
public class Rooms implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "room_id", type = IdType.AUTO)
    private Long roomId;
    private String roomNumber;
    private String roomType;
    private String roomGender;
    private Long bedCount;
    private Long wardId;
    private Long officeId;
    private Long campusId;
    private Long floorsId;
}
