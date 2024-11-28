package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.example.dto.assignbed.patientChangeBedDTO;
import com.example.dto.assignbed.patientInformationDTO;
import com.example.dto.assignbed.patientPreassignbedDTO;
import com.example.pojo.patientInformation;
import com.example.vo.assginbed.Result;
import com.example.vo.assginbed.getOnBed.getOnBedVo;
import com.example.vo.assginbed.getOutBed.getOutBedVo;


import java.util.List;

public interface AssignBedService extends IService<patientInformation> {
    //患者通知
    Result<String> inform(String id);

    //患者入院
    Result<String> inHospital(patientInformationDTO patientInformationDTO);
    //患者出院
    Result<String> discharge(patientPreassignbedDTO patientPreassignbedDTO);

    //预分配床位
    Result<String> preassign(patientPreassignbedDTO patientPreassignbedDTO);

    //传入空床位
    List<getOutBedVo> getOutBed();

    //传入占用床位
    List<getOnBedVo> getOnBed();

    //更改床位
    Result<String> changeBed(patientChangeBedDTO patientChangeBedDTO);
}
