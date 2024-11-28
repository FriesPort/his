package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.dto.assignbed.patientChangeBedDTO;
import com.example.dto.assignbed.patientInformationDTO;
import com.example.dto.assignbed.patientPreassignbedDTO;
import com.example.pojo.bed;
import com.example.pojo.patientInformation;
import com.example.mapper.AssignBedMapper;
import com.example.service.AssignBedService;
import com.example.service.IBedService;
import com.example.service.IRoomService;

import com.example.vo.assginbed.Result;
import com.example.vo.assginbed.getOnBed.getOnBedVo;
import com.example.vo.assginbed.getOutBed.getOutBedVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public Result<String> discharge(patientPreassignbedDTO patientPreassignbedDTO) {
        Result<String> result = new Result<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        patientPreassignbedDTO.setLocalDateTime(formatter.format(now));
        patientInformationDTO patientInformationDTO = new patientInformationDTO();
        int length = patientPreassignbedDTO.getId().length;
        int change = 0;
        boolean update = false;
        for(int i = 0;i<length;i++){
            patientInformationDTO.setId(patientPreassignbedDTO.getId()[i]);
            patientInformationDTO.setBedId(patientPreassignbedDTO.getBedId()[i]);
            patientInformationDTO.setName(patientPreassignbedDTO.getName());
            patientInformationDTO.setLocalDateTime(patientPreassignbedDTO.getLocalDateTime());

            LambdaUpdateWrapper<bed> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.set(bed::getPatient_id,"0")
                    .set(bed::getIs_null,"0")
                    .set(bed::getUpdate_by,patientInformationDTO.getName())
                    .set(bed::getUpdate_time,formatter.format(now))
                    .eq(bed::getId,assignBedMapper.getBedId(patientInformationDTO.getId()));
            update = iBedService.update(lambdaUpdateWrapper);

            //更新patient_information表中的信息
            LambdaUpdateWrapper<patientInformation> lambdaUpdateWrapper1 = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper1.set(patientInformation::getIs_inhospital,0)
                    .set(patientInformation::getBed_id,"0")
                    .set(patientInformation::getUpdate_by,patientInformationDTO.getName())
                    .set(patientInformation::getUpdate_time,formatter.format(now))
                    .eq(patientInformation::getId,patientInformationDTO.getId());
            change += assignBedMapper.update(lambdaUpdateWrapper1);
        }
        //更新bed表中的信息
        if(update && change == length){
            result.setStatus(true);
            result.setMessage("床位释放成功/患者出院成功");
        }else{
            result.setStatus(false);
            result.setMessage("床位释放失败，该床位已被占用");
        }
        return result;
    }

    //预分配床位
    @Override
    public Result<String> preassign(patientPreassignbedDTO patientPreassignbedDTO) {
        Result<String> result = new Result<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        patientPreassignbedDTO.setLocalDateTime(formatter.format(now));
        int length = patientPreassignbedDTO.getId().length;
        patientInformationDTO patientInformationDTO = new patientInformationDTO();
        int change = 0;
        for(int i =0;i<length;i++){
            patientInformationDTO.setBedId(patientPreassignbedDTO.getBedId()[i]);
            patientInformationDTO.setId(patientPreassignbedDTO.getId()[i]);
            patientInformationDTO.setName(patientPreassignbedDTO.getName());
            patientInformationDTO.setLocalDateTime(patientPreassignbedDTO.getLocalDateTime());
            change += assignBedMapper.preAssignBed(patientInformationDTO);   //返回的是受影响的行数
        }

/*        //修改patient_information表中的信息
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
        iBedService.update(lambdaUpdateWrapper1);*/

        if(change!=2*length){
            result.setStatus(false);
            result.setMessage("预分配床位失败，该床位已被占用");
        }else{
            result.setStatus(true);
            result.setMessage("预分配床位成功");
        }
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
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        patientChangeBedDTO.setLocalDateTime(formatter.format(now));

        int change1 = assignBedMapper.changeBed(patientChangeBedDTO);
        int change2 = assignBedMapper.changePatient(patientChangeBedDTO);

        if(change1 == 2 && change2 ==2){
            result.setStatus(true);
            result.setMessage("床位更改成功");
        }else{
            result.setStatus(false);
            result.setMessage("床位更改失败");
        }
        return result;
    }



}
