package com.example.bed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bed.entity.Room;
import com.example.vo.bed.RoomListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xw
 * @since 2024-04-26
 */
@Mapper
public interface RoomsMapper extends BaseMapper<Room> {
    List<RoomListVO> roomList();
}
