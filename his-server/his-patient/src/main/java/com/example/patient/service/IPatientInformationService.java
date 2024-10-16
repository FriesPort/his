package com.example.patient.service;

import com.example.dto.patient.PatientAlterDTO;
import com.example.dto.patient.PatientDeleteDTO;
import com.example.dto.patient.PatientEditDTO;
import com.example.dto.patient.PatientQueryDTO;
import com.example.patient.entity.Patient;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.patient.PatientVo;
import com.example.vo.patient.Result;

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

    Result<String> patientsAdd(List<PatientAlterDTO> patientList);

    Result<String> patientEdit(PatientEditDTO patientEditDTO);

    Result<String> patientDelete(PatientDeleteDTO patientDeleteDTO);

}
