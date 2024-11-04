package com.example.bed.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bed.entity.Bed;
import com.example.bed.entity.Result;
import com.example.dto.bed.BedAddDTO;
import com.example.dto.bed.BedSearchDTO;
import com.example.dto.bed.BedUpdataDTO;
import com.example.vo.JsonVO;
import com.example.vo.bed.BedVo;
import com.example.vo.bed.RoomListVO;

import java.util.ArrayList;
import java.util.List;

public interface BedsService extends IService<Bed> {

    Result<String> insertBed(String userId,BedAddDTO bedAddDTO);

    Result<String> updateBed(String userId,BedUpdataDTO bedUpdataDTO);

    Result<List<BedVo>> bedList(BedSearchDTO bedSearchDTO);

    Result<String> insertBatchBed(String userId,ArrayList<BedAddDTO> bedAddDTOs);

    List<RoomListVO> getRoomList();

    JsonVO<Boolean> deleteBed(String id);

}

