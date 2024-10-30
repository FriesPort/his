package com.example.bed.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bed.entity.Bed;
import com.example.bed.entity.Result;
import com.example.dto.bed.BedAddDTO;
import com.example.dto.bed.BedSearchDTO;
import com.example.dto.bed.BedUpdataDTO;
import com.example.vo.bed.BedVo;

import java.util.ArrayList;
import java.util.List;

public interface BedsService extends IService<Bed> {

    Result<String> insertBed(BedAddDTO bedAddDTO);

    Result<String> updateBed(BedUpdataDTO bedUpdataDTO);

    Result<List<BedVo>> bedList(BedSearchDTO bedSearchDTO);

    Result<String> insertBatchBed(ArrayList<BedAddDTO> bedAddDTOs);

}

