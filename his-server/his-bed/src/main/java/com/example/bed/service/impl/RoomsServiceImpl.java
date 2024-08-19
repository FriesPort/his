package com.example.bed.service.impl;

import com.example.bed.dto.BedSearchDTO;
import com.example.bed.entity.Room;
import com.example.bed.mapper.RoomsMapper;
import com.example.bed.service.IRoomsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xw
 * @since 2024-04-26
 */
@Service
public class RoomsServiceImpl extends ServiceImpl<RoomsMapper, Room> implements IRoomsService {

    /**
     * 先查询符合条件的房间
     * @param bedSearchDTO 床位查询条件
     * @return 返回房间列表
     */
    @Override
    public List<Room> roomList(BedSearchDTO bedSearchDTO) {
        return lambdaQuery().eq(bedSearchDTO.getWardId()!=null, Room::getWardId,bedSearchDTO.getWardId())
                .eq(bedSearchDTO.getOfficeId()!=null, Room::getOfficeId,bedSearchDTO.getOfficeId())
                .eq(bedSearchDTO.getCampusId()!=null, Room::getCampusId,bedSearchDTO.getCampusId())
                .eq(bedSearchDTO.getRoomType()!=null, Room::getRoomType,bedSearchDTO.getRoomType())
                .eq(bedSearchDTO.getBedCount()!=null, Room::getBedCount,bedSearchDTO.getBedCount())
                .eq(bedSearchDTO.getRoomGender()!=null, Room::getRoomGender,bedSearchDTO.getRoomGender())
                .list();
    }
}