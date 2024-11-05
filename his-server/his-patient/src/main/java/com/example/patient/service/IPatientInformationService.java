package com.example.patient.service;

import com.example.dto.patient.PatientAlterDTO;
import com.example.dto.patient.PatientDeleteDTO;
import com.example.dto.patient.PatientEditDTO;
import com.example.dto.patient.PatientQueryDTO;
import com.example.patient.entity.Patient;
import com.example.vo.patient.PatientVo;
import com.example.vo.patient.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xw
 * @since 2024-05-09
 */
public interface IPatientInformationService extends IService<Patient> {
    List<Patient> patientList(PatientQueryDTO patientQueryDTO);
    //患者查看
    List<PatientVo> patientQuery(PatientQueryDTO patientQueryDTO);

//    Result<String> patientAdd(String userId,PatientAlterDTO patientAlterDTO);

    Result<String> patientsAdd(String userId,List<PatientAlterDTO> patientList);

    Result<String> patientEdit(String userId,PatientEditDTO patientEditDTO);

    Result<String> patientDelete(PatientDeleteDTO patientDeleteDTO);

}
