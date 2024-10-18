package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.patientChangeBedDTO;
import com.example.dto.patientInformationDTO;
import com.example.pojo.bed;
import com.example.pojo.patientInformation;
import com.example.mapper.AssignBedMapper;
import com.example.service.AssignBedService;
import com.example.service.IBedService;
import com.example.service.IRoomService;
import com.example.vo.Result;
import com.example.vo.getOnBed.getOnBedVo;
import com.example.vo.getOutBed.getOutBedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Assign bed.
 */
@Service
public class AssignBedImpl extends ServiceImpl<AssignBedMapper,patientInformation> implements AssignBedService {

    @Autowired
    private IBedService iBedService;

    @Autowired
    private IRoomService iRoomService;

    @Autowired
    private AssignBedMapper assignBedMapper;

    //患者通知
    @Override
    public Result<String> inform(String id) {
        Result<String> result = new Result<>();
        QueryWrapper<patientInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name","gender","age","address","identity","phone","admission_number","admission_type"
                ,"book_type","is_emergency","is_vip","is_acute","preAssignBed","illness").eq("id",id);
        assignBedMapper.selectList(queryWrapper);
        result.setMessage("查询到患者信息，通知患者");
        result.setStatus(true);
        return result;
    }

    //患者入院
    @Override
    public Result<String> inHospital(patientInformationDTO patientInformationDTO) {
        Result<String> result = new Result<>();
        LambdaUpdateWrapper<patientInformation> patientInformationLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        patientInformationLambdaUpdateWrapper
                .eq(patientInformation::getId,patientInformationDTO.getId())
                .set(patientInformation::getIs_inhospital,1)
                .set(patientInformation::getUpdate_by,patientInformationDTO.getName())
                .set(patientInformation::getUpdate_time,formatter.format(now));
        assignBedMapper.update(patientInformationLambdaUpdateWrapper);
        result.setStatus(true);
        return result;
    }

    //患者出院、患者未在规定时间内入院、患者拒绝入院
    @Override
    public Result<String> discharge(patientInformationDTO patientInformationDTO) {
        Result<String> result = new Result<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //更新bed表中的信息
        LambdaUpdateWrapper<bed> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(bed::getPatient_id,"0")
                           .set(bed::getIs_null,"0")
                           .set(bed::getUpdate_by,patientInformationDTO.getName())
                           .set(bed::getUpdate_time,formatter.format(now))
                                   .eq(bed::getId,assignBedMapper.getBedId(patientInformationDTO.getId()));
        iBedService.update(lambdaUpdateWrapper);

        //更新patient_information表中的信息
        LambdaUpdateWrapper<patientInformation> lambdaUpdateWrapper1 = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper1.set(patientInformation::getIs_inhospital,0)
                            .set(patientInformation::getBed_id,"0")
                            .set(patientInformation::getUpdate_by,patientInformationDTO.getName())
                            .set(patientInformation::getUpdate_time,formatter.format(now))
                            .eq(patientInformation::getId,patientInformationDTO.getId());
        assignBedMapper.update(lambdaUpdateWrapper1);
        result.setStatus(true);
        return result;
    }

    //预分配床位
    @Override
    public Result<String> preassign(patientInformationDTO patientInformationDTO) {
        Result<String> result = new Result<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //修改patient_information表中的信息
        LambdaUpdateWrapper<patientInformation> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(patientInformation::getBed_id,patientInformationDTO.getBedId())
                           .set(patientInformation::getUpdate_by,patientInformationDTO.getName())
                           .set(patientInformation::getUpdate_time,formatter.format(now))
                           .eq(patientInformation::getId,patientInformationDTO.getId());
        assignBedMapper.update(lambdaUpdateWrapper);

        //修改bed表中的信息
        LambdaUpdateWrapper<bed> lambdaUpdateWrapper1 = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper1.set(bed::getPatient_id,patientInformationDTO.getId())
                            .set(bed::getIs_null,1)
                            .set(bed::getUpdate_by,patientInformationDTO.getName())
                            .set(bed::getUpdate_time,formatter.format(now))
                            .eq(bed::getId,assignBedMapper.getBedId(patientInformationDTO.getId()));
        iBedService.update(lambdaUpdateWrapper1);
        result.setStatus(true);
        return result;
    }

    //获取空床位
    @Override
    public List<getOutBedVo> getOutBed() {
        List<getOutBedVo> list = assignBedMapper.selectOutBed();
        return list;
    }

    //获取占用床位
    @Override
    public List<getOnBedVo> getOnBed() {
        List<getOnBedVo> list = assignBedMapper.selectOnBed();
        return list;
    }

    //更改床位
    @Override
    public Result<String> changeBed(patientChangeBedDTO patientChangeBedDTO) {
        Result<String> result = new Result<>();
        assignBedMapper.changeBed(patientChangeBedDTO);
        assignBedMapper.changePatient(patientChangeBedDTO);
        result.setStatus(true);
        return result;
    }



}
