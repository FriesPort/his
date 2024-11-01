package com.example.patient.controller;

import com.example.dto.patient.PatientAlterDTO;
import com.example.dto.patient.PatientDeleteDTO;
import com.example.dto.patient.PatientEditDTO;
import com.example.dto.patient.PatientQueryDTO;
import com.example.patient.service.IPatientInformationService;
import com.example.vo.patient.PatientVo;
import com.example.vo.patient.Result;
import com.example.vo.JsonVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

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

    private final static Logger log = LoggerFactory.getLogger(PatientInformationController.class);

    @Autowired
    private IPatientInformationService patientInformationService;


    //todo 查询所有患者信息好像没有做
    @PostMapping("/query")
    public JsonVO<Map<String, List<PatientVo>>> query(
            @RequestBody PatientQueryDTO patientQueryDTO) {

        // 调用服务查询逻辑
        Result<Map<String, List<PatientVo>>> result = patientInformationService.patientQuery(patientQueryDTO);

        if (result.isStatus()) {
            return JsonVO.success(result.getMessage());
        }
        return JsonVO.fail(result.getMessage());
    }

    /**
     * 感觉add和adds的作用有些重复了，这里的批量导入是通过json实现的，拿我在批量里面只传入一位患者的信息也是单个导入啊
     *
     */

    @PostMapping("/add")    //新增患者
    public JsonVO<String> add(@RequestHeader("userId") String userId, @RequestBody PatientAlterDTO patientAlterDTO){
        Result<String> result = patientInformationService.patientAdd(userId, patientAlterDTO);
        if(result.isStatus()) return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @PostMapping("/adds")
    public JsonVO<String> adds(@RequestHeader("userId") String userId,@RequestBody List<PatientAlterDTO> patientList) {
        try {
            // 批量导入患者信息
            Result<String> result = patientInformationService.patientsAdd(userId,patientList);
            if (result.isStatus()) return JsonVO.success(result.getMessage());
            return JsonVO.fail(result.getMessage());
        } catch (Exception e) {
            return JsonVO.fail("导入失败：" + e.getMessage());
        }
    }



    @PostMapping ("/edit")    //编辑患者
    public JsonVO<String> edit(@RequestHeader("userId") String userId,@RequestBody PatientEditDTO patientEditDTO){
        Result<String> result = patientInformationService.patientEdit(userId,patientEditDTO);
        if(result.isStatus())return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @PostMapping("/delete")  //删除患者（物理删除）
    public JsonVO<String> delete(@RequestBody PatientDeleteDTO patientDeleteDTO){
        Result<String> result = patientInformationService.patientDelete(patientDeleteDTO);
        if(result.isStatus()) return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }
}
