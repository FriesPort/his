package com.example.bed.Service;

import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bed.entity.Bed;
import com.example.bed.entity.Campus;
import com.example.bed.entity.Result;
import com.example.bed.entity.Room;
import com.example.dto.bed.BedAddDTO;
import com.example.dto.bed.BedSearchDTO;
import com.example.dto.bed.BedUpdataDTO;
import com.example.vo.bed.BedVo;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface BedsService extends IService<Bed> {

    Result<String> insertBed(String userId,BedAddDTO bedAddDTO);

    Result<String> updateBed(String userId,BedUpdataDTO bedUpdataDTO);

    Result<List<BedVo>> bedList(BedSearchDTO bedSearchDTO);

    Result<String> insertBatchBed(String userId,ArrayList<BedAddDTO> bedAddDTOs);

}

