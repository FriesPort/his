package com.example.patient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.patient.dto.PatientAlterDTO;
import com.example.patient.dto.PatientQueryDTO;
import com.example.patient.entity.Beds;
import com.example.patient.entity.Patient;
import com.example.patient.entity.Rooms;
import com.example.patient.mapper.PatientInformationMapper;
import com.example.patient.service.IBedsService;
import com.example.patient.service.IPatientInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.patient.service.IRoomsService;
import com.example.patient.vo.PatientVo;
import com.example.patient.vo.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xw
 * @since 2024-05-09
 */
@Service
public class PatientInformationServiceImpl extends ServiceImpl<PatientInformationMapper, Patient> implements IPatientInformationService {

    @Resource
    IBedsService bedsService;

    @Resource
    IRoomsService roomsService;

    @Override   //筛选患者
    public List<Patient> patientList(PatientQueryDTO patientQueryDTO) {
        LambdaQueryWrapper<Patient> patientInformationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //植入查询条件
        patientInformationLambdaQueryWrapper.eq(patientQueryDTO.getCampusId()!=null, Patient::getCampusId,patientQueryDTO.getCampusId())
                .eq(patientQueryDTO.getOfficeId()!=null, Patient::getOfficeId,patientQueryDTO.getOfficeId())
                .eq(patientQueryDTO.getWardId()!=null, Patient::getWardId,patientQueryDTO.getWardId())
                .eq(patientQueryDTO.getName()!=null, Patient::getName,patientQueryDTO.getName())
                .eq(patientQueryDTO.getGender()!=null, Patient::getGender,patientQueryDTO.getGender())
                .eq(patientQueryDTO.getAdmissionType()!=null, Patient::getAdmissionType,patientQueryDTO.getAdmissionType())
                .eq(patientQueryDTO.getBookType()!=null, Patient::getBookType,patientQueryDTO.getBookType())
                .eq(patientQueryDTO.getRoomNumberRequirement()!=null, Patient::getRoomNumberRequirement,patientQueryDTO.getRoomNumberRequirement())
                .eq(patientQueryDTO.getRoomGenderRequirement()!=null, Patient::getRoomGenderRequirement,patientQueryDTO.getRoomGenderRequirement())
                .eq(patientQueryDTO.getRoomTypeRequirement()!=null, Patient::getRoomTypeRequirement,patientQueryDTO.getRoomTypeRequirement())
                .eq(patientQueryDTO.getStatus()!=null, Patient::getStatus,patientQueryDTO.getStatus());

        List<Patient> list = list(patientInformationLambdaQueryWrapper);
        //补充筛选条件
        if (patientQueryDTO.getWaitDay()!=null){
            // 使用 removeIf 方法移除不符合等待天数的患者
            list.removeIf(patient -> {
                long days = ChronoUnit.DAYS.between(patient.getCreateTime(), LocalDateTime.now());
                return days < patientQueryDTO.getWaitDay() || !patient.getStatus().equals("待入院");
            });
        }
        return list;
    }

    @Override   //查看患者
    public Result<List<PatientVo>> patientQuery(PatientQueryDTO patientQueryDTO){
        Result<List<PatientVo>> result = new Result<>();
        List<Patient> patients = patientList(patientQueryDTO);
        List<PatientVo> patientVos = new ArrayList<>();
        //处理，拷贝患者信息
        for (Patient patient : patients) {
            //基本信息
            PatientVo patientVo = new PatientVo();
            BeanUtils.copyProperties(patient,patientVo);
            if (patient.getAdmissionTime()!=null){
                patientVo.setAdmissionTime(patient.getAdmissionTime().format(Patient.DATE_FORMATTER));
            }
            if (patient.getDischargeTime()!=null){
                patientVo.setDischargeTime(patient.getDischargeTime().format(Patient.DATE_FORMATTER));
            }
            //创建时间格式化
            patientVo.setCreateTime(patient.getCreateTime().format(Patient.DATE_FORMATTER));
            patientVos.add(patientVo);
            //计算等待天数
            if (patient.getStatus().equals("待入院")){
                long day = ChronoUnit.DAYS.between(patient.getCreateTime(), LocalDateTime.now());
                patientVo.setWaitDay((int)day);
            }else if(patient.getStatus().equals("已入院")){
                LambdaQueryWrapper<Beds> bedsLambdaQueryWrapper = new LambdaQueryWrapper<>();
                Beds bed = bedsService.getOne(bedsLambdaQueryWrapper.eq(Beds::getPatientId, patient.getPatientId()));
                patientVo.setBedNumber(bed.getBedNumber());
                LambdaQueryWrapper<Rooms> roomsLambdaQueryWrapper = new LambdaQueryWrapper<>();
                roomsLambdaQueryWrapper.eq(Rooms::getRoomId,bed.getRoomId());
                Rooms room = roomsService.getOne(roomsLambdaQueryWrapper);
                BeanUtils.copyProperties(room,patientVo);
            }

            patientVos.sort(Comparator.comparing(PatientVo::getWaitDay, Comparator.nullsLast(Integer::compareTo)).reversed());
        }
        result.setMessage(patientVos);
        result.setStatus(true);
        return result;
    }

    @Override       //添加患者
    public Result<String> patientAdd(PatientAlterDTO patientAlterDTO) {
        Result<String> result = new Result<>();
        LambdaQueryWrapper<Patient> patientInformationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        patientInformationLambdaQueryWrapper.eq(Patient::getName,patientAlterDTO.getName())
                .eq(Patient::getPhone,patientAlterDTO.getPhone());
        if (getOne(patientInformationLambdaQueryWrapper) != null) {
            result.setMessage("该患者已创建");
            return result;
        }
        //初始化患者信息
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientAlterDTO, patient);
        LocalDateTime localDateTime = LocalDateTime.now();
        patient.setCreateTime(localDateTime);
        patient.setUpdateTime(localDateTime);
        save(patient);
        result.setMessage("创建成功");
        result.setStatus(true);
        return result;
    }

    @Override   //编辑患者
    public Result<String> patientEdit(PatientAlterDTO patientAlterDTO) {
        Result<String> result = new Result<>();
        Patient patient = getById(patientAlterDTO.getId());
        if (patient == null) {
            result.setMessage("未找到该患者");
            return result;
        }
        BeanUtils.copyProperties(patientAlterDTO,patient);
        patient.setUpdateTime(LocalDateTime.now());
        updateById(patient);
        result.setMessage("编辑成功");
        result.setStatus(true);
        return result;
    }

    @Override   //患者删除
    public Result<String> patientDelete(Long patientId) {
        Result<String> result = new Result<>();
        System.out.println("patientId = " + patientId);
        //获取患者信息
        Patient patient = getById(patientId);

        if (patient == null) {
            result.setMessage("找不到指定患者");
            return result;
        }

        //判断患者入院状态
        if (patient.getStatus().equals("已入院")) {
            //患者出院
            LambdaUpdateWrapper<Beds> bedLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            bedLambdaUpdateWrapper.set(Beds::getPatientId,null)
                    .eq(Beds::getPatientId,patientId);
            bedsService.update(bedLambdaUpdateWrapper);
        }

        //删除患者
        LambdaUpdateWrapper<Patient> patientInformationLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        patientInformationLambdaUpdateWrapper.set(Patient::getDel,1)
                .eq(Patient::getPatientId,patientId);
        update(patientInformationLambdaUpdateWrapper);

        result.setMessage("删除成功");
        result.setStatus(true);
        return result;
    }
}