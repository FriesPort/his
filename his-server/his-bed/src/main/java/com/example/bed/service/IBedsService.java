package com.example.bed.service;

import com.example.bed.entity.Bed;
import com.example.bed.entity.Room;
import com.example.bed.dto.BedAddDTO;
import com.example.bed.dto.BedDispatchDTO;
import com.example.bed.dto.BedSearchDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bed.vo.Result;
import com.example.bed.vo.bedAdd.RoomVo;
import com.example.bed.vo.bedAssign.BedVo;
import com.example.bed.vo.bedSearch.SearchVo;
import com.example.bed.vo.bedPage.CampusVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xw
 * @since 2024-04-26
 */
public interface IBedsService extends IService<Bed> {

    Result<List<CampusVo>> bedPage();

    List<Bed> bedList(List<Room> rooms, BedSearchDTO bedSearchDTO);

    List<SearchVo> bedPatientList(List<Bed> beds);

    Result<String> bedAssign(BedDispatchDTO bedDispatchDTO);

    Result<List<BedVo>> bedAssign(Integer wardId);

    Result<String> bedRelease(BedDispatchDTO bedDispatchDTO);

    Result<List<RoomVo>> bedAdd(Integer wardId);

    Result<String> bedAdd(BedAddDTO bedAddDTO);

    Result<String> bedDelete(Long bedId);
}
