package com.example.bed.controller;


import com.example.bed.dto.BedAddDTO;
import com.example.bed.dto.BedDispatchDTO;
import com.example.bed.dto.BedSearchDTO;
import com.example.bed.entity.Bed;
import com.example.bed.entity.Room;
import com.example.bed.service.IBedsService;
import com.example.bed.service.IRoomsService;
import com.example.bed.vo.Result;
import com.example.bed.vo.bedAdd.RoomVo;
import com.example.bed.vo.bedAssign.BedVo;
import com.example.bed.vo.bedPage.CampusVo;
import com.example.bed.vo.bedSearch.SearchVo;
import com.example.vo.JsonVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private IBedsService bedsService;
    @Autowired
    private IRoomsService roomsService;

    @GetMapping //进入页面时，提供区域信息
    public JsonVO<List<CampusVo>> bed(){
        Result<List<CampusVo>> result = bedsService.bedPage();
        if(result.isStatus()) return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @GetMapping("/list")    //查看符合条件的床位
    public JsonVO<List<SearchVo>> list(
            @RequestParam(required = false) Integer wardId,
            @RequestParam(required = false) Integer officeId,
            @RequestParam(required = false) Integer campusId,
            @RequestParam(required = false) String bedType,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) Integer bedCount,
            @RequestParam(required = false) String roomGender) {

        BedSearchDTO bedSearchDTO = new BedSearchDTO(wardId, officeId, campusId, bedType, roomType, bedCount, roomGender);
        System.out.println("bedSearchDTO = " + bedSearchDTO);
        //先查出符合条件的房间
        List<Room> rooms = roomsService.roomList(bedSearchDTO);
        for (Room room : rooms) {
            System.out.println("room = " + room);
        }
        //查出房间列表内所有符合条件的床位
        List<Bed> beds = bedsService.bedList(rooms,bedSearchDTO);
        for (Bed bed : beds) {
            System.out.println("bed = " + bed);
        }
        //获取床位相关信息
        List<SearchVo> searchVos = bedsService.bedPatientList(beds);
        return JsonVO.success(searchVos);
    }

    @GetMapping("/add/{wardId}")    //添加床位，提供病区房间
    public JsonVO<List<RoomVo>> add(@PathVariable Integer wardId){
        Result<List<RoomVo>> result = bedsService.bedAdd(wardId);
        if(result.isStatus()) return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @PostMapping("/add")    //添加床位
    public JsonVO<String> add(@RequestBody BedAddDTO bedAddDTO){
        Result<String> result = bedsService.bedAdd(bedAddDTO);
        if(result.isStatus()) return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @GetMapping("/assign/{wardId}") //获取病区空闲床位
    public JsonVO<List<BedVo>> assign(@PathVariable Integer wardId){
        Result<List<BedVo>> result = bedsService.bedAssign(wardId);
        if(result.isStatus()) return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @PutMapping("/assign")  //分配床位
    public JsonVO<String> assign(@RequestBody BedDispatchDTO bedDispatchDTO){
        Result<String> result = bedsService.bedAssign(bedDispatchDTO);
        if(result.isStatus()) return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @PutMapping("/release")    //释放床位，患者出院
    public JsonVO<String> release(@RequestBody BedDispatchDTO bedDispatchDTO){
        Result<String> result = bedsService.bedRelease(bedDispatchDTO);
        if(result.isStatus()) return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @DeleteMapping("/delete/{bedId}") //删除床位
    public JsonVO<String> delete(@PathVariable Long bedId){
        Result<String> result = bedsService.bedDelete(bedId);
        if(result.isStatus()) return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }
}