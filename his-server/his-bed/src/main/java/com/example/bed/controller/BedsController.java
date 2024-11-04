package com.example.bed.controller;


import com.example.bed.Service.BedsService;
import com.example.dto.bed.BedAddDTO;
import com.example.dto.bed.BedSearchDTO;
import com.example.bed.entity.Result;

import com.example.dto.bed.BedUpdataDTO;
import com.example.vo.bed.BedVo;
import com.example.vo.JsonVO;

import com.example.vo.bed.RoomListVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xw
 * @since 2024-04-26
 */
@RestController
@RequestMapping("/beds")
public class BedsController {

    @Autowired
    private BedsService bedsService;

    //添加床位
    //todo 检测添加的床位类型（男床女床）是否和所属房间类型不同（房间性别）
    @ApiOperation("单个添加床位")
    @PostMapping("/add")
    public JsonVO<String> add(@ApiParam("添加的床位信息") @RequestHeader("userId")String userId, @RequestBody BedAddDTO bedAddDTO) {
        Result<String> result = bedsService.insertBed(userId,bedAddDTO);
        if (result.isStatus()) {
            return JsonVO.success(result.getMessage());
        }
        return JsonVO.fail(result.getMessage());
    }


    //床位信息修改
    //todo 传入修改的字段名：字段值
    @ApiOperation("床位信息修改")
    @PostMapping("/update")
    public JsonVO<String> update(@ApiParam("修改的床位信息") @RequestHeader("userId")String userId,@RequestBody BedUpdataDTO bedUpdataDTO) {
        Result<String> result = bedsService.updateBed(userId,bedUpdataDTO);
        if (result.isStatus()) {
            return JsonVO.success(result.getMessage());
        }
        return JsonVO.fail(result.getMessage());
    }


    //床位信息显示（床位信息显示应该不包含病房的查询）
    //todo 显示病房号
    @ApiOperation("床位信息显示")
    @GetMapping("/list")
    public JsonVO<List<BedVo>> list(@ApiParam("检索条件")BedSearchDTO bedSearchDTO) {
        Result<List<BedVo>> result = bedsService.bedList(bedSearchDTO);
        if (result.isStatus()) {
            return JsonVO.success(result.getMessage());
        }
        return JsonVO.fail(result.getMessage());
    }


    //批量添加床位
    @ApiOperation("批量添加床位")
    @PostMapping("/addBatch")
    public JsonVO<String> addBatch(@ApiParam("Json数据") @RequestHeader("userId")String userId,@RequestBody ArrayList<BedAddDTO> bedAddDTOList) {
        Result<String> result = bedsService.insertBatchBed(userId,bedAddDTOList);
        if (result.isStatus()) {
            return JsonVO.success(result.getMessage());
        }
        return JsonVO.fail(result.getMessage());
    }

    /**
     * 获取房间信息列表
     * @return
     */
    @GetMapping("/roomList")
    public JsonVO<List<RoomListVO>> roomList() {
        return JsonVO.success(bedsService.getRoomList());
    }

    /**
     * 床位删除
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public JsonVO<Boolean> bedDelete(@ApiParam("要删除的床位id") @RequestParam String id) {
        return bedsService.deleteBed(id);
    }


}
