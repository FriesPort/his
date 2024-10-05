package com.example.patient.controller;

import com.example.patient.dto.PatientAlterDTO;
import com.example.patient.dto.PatientQueryDTO;
import com.example.patient.service.IPatientInformationService;
import com.example.patient.vo.PatientVo;
import com.example.patient.vo.Result;
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
 * @since 2024-05-09
 */
@RestController
@RequestMapping("/patient")
public class PatientInformationController {

    @Autowired
    private IPatientInformationService patientInformationService;

    @GetMapping("/query")   //查询患者
    public JsonVO<List<PatientVo>> query(
            @RequestParam(required = false) Integer campusId,
            @RequestParam(required = false) Integer officeId,
            @RequestParam(required = false) Integer wardId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String admissionType,
            @RequestParam(required = false) String bookType,
            @RequestParam(required = false) String roomNumberRequirement,
            @RequestParam(required = false) String roomGenderRequirement,
            @RequestParam(required = false) String roomTypeRequirement,
            @RequestParam(required = false) Integer waitDay,
            @RequestParam(required = false) String status) {
        PatientQueryDTO patientQueryDTO = new PatientQueryDTO(campusId,officeId,wardId,name,gender,admissionType,bookType,roomNumberRequirement,roomGenderRequirement,roomTypeRequirement,waitDay,status);
        Result<List<PatientVo>> result = patientInformationService.patientQuery(patientQueryDTO);
        if(result.isStatus())return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @PostMapping("/add")    //新增患者
    public JsonVO<String> add(@RequestBody PatientAlterDTO patientAlterDTO){
        Result<String> result = patientInformationService.patientAdd(patientAlterDTO);
        if(result.isStatus())return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @PutMapping("/edit")    //编辑患者
    public JsonVO<String> edit(@RequestBody PatientAlterDTO patientAlterDTO){
        Result<String> result = patientInformationService.patientEdit(patientAlterDTO);
        if(result.isStatus())return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @DeleteMapping("/delete/{patientId}")  //删除患者（逻辑删除）
    public JsonVO<String> delete(@PathVariable Long patientId){
        Result<String> result = patientInformationService.patientDelete(patientId);
        if(result.isStatus()) return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }
}
