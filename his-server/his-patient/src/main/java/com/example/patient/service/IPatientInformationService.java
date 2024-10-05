package com.example.patient.service;

import com.example.patient.dto.PatientAlterDTO;
import com.example.patient.dto.PatientQueryDTO;
import com.example.patient.entity.Patient;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.patient.vo.PatientVo;
import com.example.patient.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xw
 * @since 2024-05-09
 */
public interface IPatientInformationService extends IService<Patient> {

    //查询符合条件的患者信息列表
    List<Patient> patientList(PatientQueryDTO patientQueryDTO);

    //患者查看
    Result<List<PatientVo>> patientQuery(PatientQueryDTO patientQueryDTO);

    Result<String> patientAdd(PatientAlterDTO patientAlterDTO);

    Result<String> patientEdit(PatientAlterDTO patientAlterDTO);

    Result<String> patientDelete(Long patientId);
}
