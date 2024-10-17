package com.example.patient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.patient.PatientAlterDTO;
import com.example.dto.patient.PatientDeleteDTO;
import com.example.dto.patient.PatientEditDTO;
import com.example.dto.patient.PatientQueryDTO;
import com.example.patient.entity.Patient;
import com.example.patient.mapper.PatientInformationMapper;
import com.example.patient.service.IPatientInformationService;
import com.example.utils.IdGenerate;
import com.example.utils.TimeTrainsform;
import com.example.vo.patient.PatientVo;
import com.example.vo.patient.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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


    @Autowired
    private IdGenerate idGenerate;

   @Autowired
   private TimeTrainsform timeTrainsform;

    private static Logger logger = LoggerFactory.getLogger(PatientInformationServiceImpl.class);

    @Override // 筛选患者
    public List<Patient> patientList(PatientQueryDTO patientQueryDTO) {
        LambdaQueryWrapper<Patient> patientInformationLambdaQueryWrapper = new LambdaQueryWrapper<>();



        // 植入查询条件
        patientInformationLambdaQueryWrapper
                .eq(patientQueryDTO.getId() != null, Patient::getId, patientQueryDTO.getId())
                .eq(patientQueryDTO.getName() != null, Patient::getName, patientQueryDTO.getName())
                .eq(patientQueryDTO.getGender() != null, Patient::getGender, patientQueryDTO.getGender())
                .eq(patientQueryDTO.getAge() != null, Patient::getAge, patientQueryDTO.getAge())
                .eq(patientQueryDTO.getAddress() != null, Patient::getAddress, patientQueryDTO.getAddress())
                .eq(patientQueryDTO.getIdentity() != null, Patient::getIdentity, patientQueryDTO.getIdentity())
                .eq(patientQueryDTO.getPhone() != null, Patient::getPhone, patientQueryDTO.getPhone())
                .eq(patientQueryDTO.getAdmissionNumber() != null, Patient::getAdmissionnumber, patientQueryDTO.getAdmissionNumber())
                .eq(patientQueryDTO.getAdmissionType() != null, Patient::getAdmissiontype, patientQueryDTO.getAdmissionType())
                .eq(patientQueryDTO.getBedId() != null, Patient::getBedId, patientQueryDTO.getBedId())
                .eq(patientQueryDTO.getBookType() != null, Patient::getBooktype, patientQueryDTO.getBookType())
                .eq(patientQueryDTO.getIsEmergency() != null, Patient::getIsemergency, patientQueryDTO.getIsEmergency())
                .eq(patientQueryDTO.getIsVip() != null, Patient::getIsvip, patientQueryDTO.getIsVip())
                .eq(patientQueryDTO.getIsAcute() != null, Patient::getIsacute, patientQueryDTO.getIsAcute())
                .eq(patientQueryDTO.getIsInHospital() != null, Patient::getIsInhospital, patientQueryDTO.getIsInHospital())
                .eq(patientQueryDTO.getPreAssignBed() != null, Patient::getPreassignbed, patientQueryDTO.getPreAssignBed())
                .eq(patientQueryDTO.getIllness() != null, Patient::getIllness, patientQueryDTO.getIllness())
                .eq(patientQueryDTO.getCreateBy() != null, Patient::getCreateby, patientQueryDTO.getCreateBy())
                .eq(patientQueryDTO.getUpdateBy() != null, Patient::getUpdateBy, patientQueryDTO.getUpdateBy());

// 处理入院时间（Admission Time）
        if (patientQueryDTO.getAdmissionTime() != null) {
            patientInformationLambdaQueryWrapper.between(
                    Patient::getAdmissiontime,
                    timeTrainsform.getStartOfDay(patientQueryDTO.getAdmissionTime()),
                    timeTrainsform.getEndOfDay(patientQueryDTO.getAdmissionTime())
            );
        }

// 处理创建时间（Create Time）
        if (patientQueryDTO.getCreateTime() != null) {
            patientInformationLambdaQueryWrapper.between(
                    Patient::getCreateTime,
                    timeTrainsform.getStartOfDay(patientQueryDTO.getCreateTime()),
                    timeTrainsform.getEndOfDay(patientQueryDTO.getCreateTime())
            );
        }

// 处理更新时间（Update Time）
        if (patientQueryDTO.getUpdateTime() != null) {
            patientInformationLambdaQueryWrapper.between(
                    Patient::getUpdateTime,
                    timeTrainsform.getStartOfDay(patientQueryDTO.getUpdateTime()),
                    timeTrainsform.getEndOfDay(patientQueryDTO.getUpdateTime())
            );
        }


        return list(patientInformationLambdaQueryWrapper);
    }


    @Override   // 查看患者
    public Result<List<PatientVo>> patientQuery(PatientQueryDTO patientQueryDTO) {
        Result<List<PatientVo>> result = new Result<>();
        List<Patient> patients = patientList(patientQueryDTO);
        List<PatientVo> patientVos = new ArrayList<>();


        // 处理，拷贝患者信息
        for (Patient patient : patients) {
            PatientVo patientVo = new PatientVo();
            BeanUtils.copyProperties(patient, patientVo);

            // 设置时间格式
            if (patient.getAdmissiontime() != null) {
                patientVo.setAdmissiontime(patient.getAdmissiontime().format(Patient.DATE_FORMATTER));
            }
            if (patient.getDischargeTime() != null) {
                patientVo.setDischargeTime(patient.getDischargeTime().format(Patient.DATE_FORMATTER));
            }
            patientVo.setCreateTime(patient.getCreateTime().format(Patient.DATE_FORMATTER));

            // 计算等待天数
            if (patient.getIsInhospital() == 0) { // 判断是否在住院
                long day = ChronoUnit.DAYS.between(patient.getCreateTime(), LocalDateTime.now());
                patientVo.setWaitDay((int) day);
            } else {
                patientVo.setWaitDay(0); // 非待入院患者等待天数设置为0
            }

            patientVos.add(patientVo);
        }

        // 排序逻辑：先按是否住院排序，再按急重症和等待时间排序
        patientVos.sort(Comparator
                .comparing(PatientVo::getIsInhospital)  // 按是否在住院排序
                .thenComparing((PatientVo p) -> {
                    if (p.getIsemergency() == 1) return 1;  // 急诊优先
                    if (p.getIsacute() == 1) return 2;      // 重症其次
                    return 3;                               // 其他排最后
                })
                .thenComparing(PatientVo::getWaitDay, Comparator.reverseOrder())  // 按等待时间降序
        );

        result.setMessage(patientVos);
        result.setStatus(true);
        return result;
    }

    @Override
    public Result<String> patientAdd(PatientAlterDTO patientAlterDTO) {
        Result<String> result = new Result<>();
        LambdaQueryWrapper<Patient> patientInformationLambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 初始化患者信息
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientAlterDTO, patient);

        if(patientAlterDTO.getName()==null){
            result.setMessage("请填写名字");
            return result;
        }

        if(patientAlterDTO.getIdentity()!=null){
        patientInformationLambdaQueryWrapper.eq(Patient::getIdentity,patientAlterDTO.getIdentity());
        if(getOne(patientInformationLambdaQueryWrapper)!=null){
            result.setMessage("患者(身份证号码为" + patientAlterDTO.getIdentity() + "）已被创建");
    return result;
}
        }

        // 使用 IdGenerate 生成 UUID 作为主键
        String generatedId = idGenerate.nextUUID(patient);
        patient.setId(generatedId);  // 假设 Patient 有 id 字段

        // 将字符串格式的入院时间转换为 LocalDateTime
        if (patientAlterDTO.getAdmissiontime() != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime admissionTime = LocalDateTime.parse(patientAlterDTO.getAdmissiontime(), formatter);
                patient.setAdmissiontime(admissionTime); // 设置入院时间
            } catch (DateTimeParseException e) {
                result.setMessage("入院时间格式错误，请提供正确的格式 (yyyy-MM-dd HH:mm:ss)");
                result.setStatus(false);
                return result;
            }
        }
        // 设置当前时间为创建时间
        LocalDateTime localDateTime = LocalDateTime.now();
        patient.setCreateTime(localDateTime);
        patient.setUpdateTime(localDateTime);

        // 保存患者信息
        save(patient);

        result.setMessage("创建成功");
        result.setStatus(true);
        return result;
    }

    @Transactional
    @Override
    public Result<String> patientsAdd(List<PatientAlterDTO> patientList) {
        Result<String> result = new Result<>();

        for (PatientAlterDTO patientAlterDTO : patientList) {
            // 检查是否已存在相同身份证号的患者

            if(patientAlterDTO.getName()==null){
                throw new RuntimeException("请填写名字，请重新导入这一批数据");
            }

            if(patientAlterDTO.getIdentity()!=null){
                LambdaQueryWrapper<Patient> patientInformationLambdaQueryWrapper = new LambdaQueryWrapper<>();
                patientInformationLambdaQueryWrapper.eq(Patient::getIdentity,patientAlterDTO.getIdentity());
                if(getOne(patientInformationLambdaQueryWrapper)!=null){
                    throw new RuntimeException("患者(身份证号码为" + patientAlterDTO.getIdentity() + "）已被创建，请重新导入这一批数据");
                }
            }


            // 初始化患者信息
            Patient patient = new Patient();
            BeanUtils.copyProperties(patientAlterDTO, patient);

            // 生成 UUID 作为主键
            String generatedId = idGenerate.nextUUID(patient);
            patient.setId(generatedId);

            // 将字符串格式的入院时间转换为 LocalDateTime
            if (patientAlterDTO.getAdmissiontime() != null) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime admissionTime = LocalDateTime.parse(patientAlterDTO.getAdmissiontime(), formatter);
                    patient.setAdmissiontime(admissionTime); // 设置入院时间
                } catch (DateTimeParseException e) {
                    result.setMessage("入院时间格式错误，请提供正确的格式 (yyyy-MM-dd HH:mm:ss)");
                    result.setStatus(false);
                    return result;
                }
            }

            LocalDateTime now = LocalDateTime.now();
            patient.setCreateTime(now);
            patient.setUpdateTime(now);

            // 保存患者信息
            save(patient);
        }

        result.setMessage("批量导入完成");
        result.setStatus(true);
        return result;
    }

    @Transactional
    @Override   //编辑患者
    public Result<String> patientEdit(PatientEditDTO patientEditDTO) {
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

        updateById(patient);
        result.setMessage("编辑成功");
        result.setStatus(true);
        return result;
    }

    @Override   //患者删除
    public Result<String> patientDelete(PatientDeleteDTO patientDeleteDTO) {
        Result<String> result = new Result<>();
        // 初始化患者信息
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDeleteDTO, patient);
        String patientId=patient.getId();
        logger.info("要删除的ID:{}",patientId);
        //获取患者信息
        patient=getById(patientId);

        if (patient == null) {
            result.setMessage("找不到指定患者");
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

        if(patient.getIsInhospital()==1){

        }

        //删除患者
        LambdaUpdateWrapper<Patient> patientInformationLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        patientInformationLambdaUpdateWrapper.eq(Patient::getId,patientId);
        remove(patientInformationLambdaUpdateWrapper);


        result.setMessage("删除成功");
        result.setStatus(true);
        return result;
    }
}