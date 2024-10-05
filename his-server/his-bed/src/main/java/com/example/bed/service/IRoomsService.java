package com.example.bed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bed.dto.BedSearchDTO;
import com.example.bed.entity.Room;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xw
 * @since 2024-04-26
 */
public interface IRoomsService extends IService<Room> {

    List<Room> roomList(BedSearchDTO bedSearchDTO);

}
