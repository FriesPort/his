package com.example.patient.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.patient.PatientAlterDTO;
import com.example.dto.patient.PatientDeleteDTO;
import com.example.dto.patient.PatientEditDTO;
import com.example.dto.patient.PatientQueryDTO;
import com.example.patient.entity.*;
import com.example.patient.mapper.AllIdMapper;
import com.example.patient.mapper.PatientInformationMapper;
import com.example.patient.mapper.PatientMapper;
import com.example.patient.service.IPatientInformationService;
import com.example.patient.service.IPatientrecodeService;
import com.example.utils.IdGenerate;
import com.example.utils.TimeTrainsform;
import com.example.vo.patient.PatientVo;
import com.example.vo.patient.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xw
 * @since 2024-05-09
 */
@Slf4j
@Service
public class PatientInformationServiceImpl extends ServiceImpl<PatientInformationMapper, Patient> implements IPatientInformationService {


    @Autowired
    private IPatientrecodeService patientrecodeService;

    @Autowired
    private IdGenerate idGenerate;

    @Autowired
    private AllIdMapper allIdMapper;
    @Autowired
    private PatientInformationMapper patientInformationMapper;


    private static Logger logger = LoggerFactory.getLogger(PatientInformationServiceImpl.class);

    @Override   //筛选患者
    public List<Patient> patientList(PatientQueryDTO patientQueryDTO) {
        LambdaQueryWrapper<AllId> allIdLambdaQueryWrapper = new LambdaQueryWrapper<>();
        allIdLambdaQueryWrapper
                .select(AllId::getPatientId)
                .eq(StrUtil.isNotEmpty(patientQueryDTO.getCampusId()),AllId::getCampusId,patientQueryDTO.getCampusId())
                .eq(StrUtil.isNotEmpty(patientQueryDTO.getWardId()),AllId::getWardId,patientQueryDTO.getWardId())
                .eq(StrUtil.isNotEmpty(patientQueryDTO.getRoomGenderRequirement()),AllId::getRoomGender,patientQueryDTO.getRoomGenderRequirement())
                .eq(StrUtil.isNotEmpty(patientQueryDTO.getRoomTypeRequirement()),AllId::getRoomType,patientQueryDTO.getRoomTypeRequirement())
                .eq(StrUtil.isNotEmpty(patientQueryDTO.getRoomNumberRequirement()),AllId::getRoomNumber,patientQueryDTO.getRoomNumberRequirement());
        List<String> patientId=allIdMapper.selectObjs(allIdLambdaQueryWrapper);

        LambdaQueryWrapper<Patient> patientInformationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!patientId.isEmpty()){
            patientInformationLambdaQueryWrapper.in(Patient::getId,patientId);
        }

        //植入查询条件
        patientInformationLambdaQueryWrapper
                .eq(patientQueryDTO.getName()!=null, Patient::getName,patientQueryDTO.getName())
                .eq(patientQueryDTO.getGender()!=null, Patient::getGender,patientQueryDTO.getGender())
                .eq(patientQueryDTO.getAdmissionType()!=null, Patient::getAdmissiontype,patientQueryDTO.getAdmissionType())
                .eq(patientQueryDTO.getStatus()!=null, Patient::getIsInhospital,patientQueryDTO.getStatus());

        List<Patient> list = patientInformationMapper.selectList(patientInformationLambdaQueryWrapper);
        //补充筛选条件
        if (patientQueryDTO.getWaitDay()!=null){
            // 使用 removeIf 方法移除不符合等待天数的患者
            list.removeIf(patient -> {
                long days = ChronoUnit.DAYS.between(patient.getCreateTime(), LocalDateTime.now());
                return days < patientQueryDTO.getWaitDay() || !(patient.getIsInhospital()==0);
            });
        }

        return list.stream()
                .sorted((p1,p2) -> {
                    int i=Integer.compare(p1.getIsInhospital(),p2.getIsInhospital());
                    if(i!=0){
                        return i;
                    }
                    return Integer.compare(p1.getIsacute(),p2.getIsacute());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override   // 查看患者
    public List<PatientVo> patientQuery(PatientQueryDTO patientQueryDTO) {


    }

    @Override
    public Result<String> patientsAdd(String userId,List<PatientAlterDTO> patientList) {
        Result<String> result = new Result<>();

        for (PatientAlterDTO patientAlterDTO : patientList) {

            LambdaQueryWrapper<Patient> patientInformationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            LambdaQueryWrapper<Patient> patientInformationLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            LambdaQueryWrapper<Patient> patientInformationLambdaQueryWrapper2 = new LambdaQueryWrapper<>();

            // 初始化患者信息
            Patient patient = new Patient();


            if(patientAlterDTO.getName()==null){
                result.setMessage("请填写名字");
                return result;
            }

            patientInformationLambdaQueryWrapper.eq(Patient::getIdentity,patientAlterDTO.getIdentity())
                    .isNull(Patient::getDischargeTime);

            if(patientAlterDTO.getIdentity()!=null&&getOne(patientInformationLambdaQueryWrapper)!=null){
                result.setMessage("患者(身份证号码为" + patientAlterDTO.getIdentity() + "）已被创建");
                return result;
            }



            if(patientAlterDTO.getIdentity()!=null){
                patientInformationLambdaQueryWrapper1.eq(Patient::getIdentity,patientAlterDTO.getIdentity())
                        .isNotNull(Patient::getDischargeTime);
                Patient existingPatient = getOne(patientInformationLambdaQueryWrapper1);
                if(existingPatient!=null){
                    LambdaUpdateWrapper<Patient> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(Patient::getId, existingPatient.getId())
                            .set(Patient::getAdmissionnumber, null)
                            .set(Patient::getAdmissiontype, null)
                            .set(Patient::getAdmissiontime, null)
                            .set(Patient::getDischargeTime, null)
                            .set(Patient::getBedId, null)
                            .set(Patient::getBooktype, null)
                            .set(Patient::getIsemergency, 0)
                            .set(Patient::getIsvip, 0)
                            .set(Patient::getIsacute, 0)
                            .set(Patient::getIsInhospital, 0)
                            .set(Patient::getPreassignbed, null)
                            .set(Patient::getIllness, null);
                    update(existingPatient, updateWrapper);
                    patientInformationLambdaQueryWrapper2.eq(Patient::getIdentity,patientAlterDTO.getIdentity());
                    patient=getOne(patientInformationLambdaQueryWrapper2);
                    BeanUtils.copyProperties(patientAlterDTO, patient);
                    LocalDateTime localDateTime = LocalDateTime.now();
                    patient.setUpdateTime(localDateTime);
                    patient.setUpdateBy(userId);
                    updateById(patient);
                }
            }

            BeanUtils.copyProperties(patientAlterDTO, patient);



            // 设置当前时间为创建时间
            LocalDateTime localDateTime = LocalDateTime.now();
            patient.setCreateTime(localDateTime);
            patient.setUpdateTime(localDateTime);
            patient.setCreateby(userId);
            patient.setUpdateBy(userId);

            // 使用 IdGenerate 生成 UUID 作为主键
            String generatedId = idGenerate.nextUUID(patient);
            patient.setId(generatedId);  // 假设 Patient 有 id 字段


            // 保存患者信息
            save(patient);
        }

        result.setMessage("批量导入完成");
        result.setStatus(true);
        return result;
    }

    @Transactional
    @Override   //编辑患者
    public Result<String> patientEdit(String userId,PatientEditDTO patientEditDTO) {
        Result<String> result = new Result<>();
        Patient patient = getById(patientEditDTO.getId());
        if (patient == null) {
            result.setMessage("未找到该患者");
            return result;
        }

        if(patientEditDTO.getIdentity()!=null) {
            LambdaQueryWrapper<Patient> patientLambdaQueryWrapper = new LambdaQueryWrapper<>();
            patientLambdaQueryWrapper.eq(Patient::getIdentity, patientEditDTO.getIdentity())
                    .ne(Patient::getId, patient.getId());
            if (getOne(patientLambdaQueryWrapper) != null) {
                result.setMessage("该患者已被创建");
                return result;
            }
        }

        BeanUtils.copyProperties(patientEditDTO,patient);
        patient.setUpdateTime(LocalDateTime.now());

        // 将字符串格式的入院时间转换为 LocalDateTime
        if (patientEditDTO.getAdmissiontime() != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime admissionTime = LocalDateTime.parse(patientEditDTO.getAdmissiontime(), formatter);
                patient.setAdmissiontime(admissionTime); // 设置入院时间
            } catch (DateTimeParseException e) {
                result.setMessage("入院时间格式错误，请提供正确的格式 (yyyy-MM-dd HH:mm:ss)");
                result.setStatus(false);
                return result;
            }
        }

        patient.setUpdateBy(userId);

        updateById(patient);
        result.setMessage("编辑成功");
        result.setStatus(true);
        return result;
    }

    @Transactional
    @Override   //患者删除
    public Result<String> patientDelete(PatientDeleteDTO patientDeleteDTO) {
        Result<String> result = new Result<>();
        // 初始化患者信息
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDeleteDTO, patient);
        String patientId=patient.getId();
        logger.info("要释放的ID:{}",patientId);
        //获取患者信息
        patient=getById(patientId);

        if (patient == null) {
            result.setMessage("找不到指定患者");
            return result;
        }

        if(patient.getDischargeTime()!=null){
            result.setMessage("该患者已出院");
            return result;
        }

//        //判断患者入院状态
//        if (patient.getStatus().equals("已入院")) {
//            //患者出院
//            LambdaUpdateWrapper<Beds> bedLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//            bedLambdaUpdateWrapper.set(Beds::getPatientId,null)
//                    .eq(Beds::getPatientId,patientId);
//            bedsService.update(bedLambdaUpdateWrapper);
//        }


        //置入住院记录当中


        if (patient.getIsInhospital() != 0) { // 判断是否在住院
            // 判断患者是否住院
            // 创建 Patientrecode 对象并复制患者信息
            Patientrecode patientrecode = new Patientrecode();
            patientrecode.setTotalCost(new BigDecimal("0.00"));
            // 设置默认的BedId
            patientrecode.setBedId("无");
            LocalDateTime minDate=LocalDateTime.of(1000,1,1,0,0);
            patientrecode.setAdmissionTime(minDate);
            String generatedId = idGenerate.nextUUID(patient);
            patientrecode.setId(generatedId);

            patientrecode.setPatientId(patient.getId());
            if(patient.getBedId()!=null) {
                patientrecode.setBedId(patient.getBedId());
            }
            if(patient.getAdmissiontime()!=null) {
                patientrecode.setAdmissionTime(patient.getAdmissiontime());
            }
            patientrecode.setDischargeTime(LocalDateTime.now()); // 设置出院时间为当前时间
            patientrecode.setAdmissionDiagnosis(patient.getIllness());
            patientrecode.setDischargeDiagnosis(patientDeleteDTO.getDischargeDiagnosis());
            patientrecode.setSymptoms(patient.getIllness());
            patientrecode.setTreatments(patientDeleteDTO.getTreatments());

            String bedId = patient.getBedId();

// 设置默认的 doctorId 和 nurseId
            patientrecode.setDoctorId("无");
            patientrecode.setNurseId("无");

            if (bedId != null) {
                // 从床位表中查找 room_id
                LambdaQueryWrapper<Bed> bedQueryWrapper = new LambdaQueryWrapper<>();
                bedQueryWrapper.eq(Bed::getBedId, bedId);
                Bed bed = bedService.getOne(bedQueryWrapper);

                if (bed != null) {
                    String roomId = bed.getRoomId();

                    if (roomId != null) {
                        // 从 room_user 表中查找 doctor_id 和 nurse_id
                        LambdaQueryWrapper<Roomuser> roomUserQueryWrapper = new LambdaQueryWrapper<>();
                        roomUserQueryWrapper.eq(Roomuser::getRoomid, roomId);
                        Roomuser roomUser = roomuserService.getOne(roomUserQueryWrapper);

                        if (roomUser != null) {
                            // 设置 doctorId，如果不为 null 则覆盖默认值
                            String doctorId = roomUser.getDoctorId();
                            if (doctorId != null) {
                                patientrecode.setDoctorId(doctorId);
                            }

                            // 获取 nurseId 列表的第一个元素
                            String nurses = roomUser.getNurseId();
                            if (nurses != null && !nurses.isEmpty()) {
                                patientrecode.setNurseId(nurses);
                            }
                        }
                    }
                }
            }



            // 计算住院费用
            if(patient.getAdmissiontime()!=null) {
                long daysInHospital = ChronoUnit.DAYS.between(patient.getAdmissiontime(), LocalDateTime.now());
                BigDecimal totalCost = BigDecimal.valueOf(daysInHospital * 100); // 每天100元
                patientrecode.setTotalCost(totalCost); // 设置总费用
            }

            // 将删除的患者信息插入到 patientrecode 表中
            boolean saved = patientrecodeService.save(patientrecode);
            if (!saved) {
                result.setMessage("保存释放的患者信息失败");
                result.setStatus(false);
                return result;
            }
        }

        //删除患者
        LambdaUpdateWrapper<Patient> patientInformationLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        patientInformationLambdaUpdateWrapper.eq(Patient::getId,patientId);
        patientInformationLambdaUpdateWrapper.set(Patient::getDischargeTime,LocalDateTime.now());
        update(patientInformationLambdaUpdateWrapper);



        result.setMessage("释放成功");
        result.setStatus(true);
        return result;
    }
}




