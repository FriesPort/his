package com.example.patient.controller;

import com.example.dto.patient.PatientAlterDTO;
import com.example.dto.patient.PatientDeleteDTO;
import com.example.dto.patient.PatientEditDTO;
import com.example.dto.patient.PatientQueryDTO;
import com.example.patient.service.IPatientInformationService;
import com.example.vo.patient.PatientVo;
import com.example.vo.patient.Result;
import com.example.vo.JsonVO;
import com.example.patient.dto.PatientAlterDTO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import com.example.patient.utils.JsonReader;
import com.example.patient.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final static Logger log = LoggerFactory.getLogger(PatientInformationController.class);

    @Resource
    private IPatientInformationService patientInformationService;

    @Resource
    private JsonReader jsonReader; // 注入 JsonReader

    @Resource
    private FileUtil fileUtil;

    @PostMapping("/query")
    public JsonVO<List<PatientVo>> query(
            @RequestBody PatientQueryDTO patientQueryDTO) {

        // 调用服务查询逻辑
        Result<List<PatientVo>> result = patientInformationService.patientQuery(patientQueryDTO);

        if (result.isStatus()) {
            return JsonVO.success(result.getMessage());
        }
        return JsonVO.fail(result.getMessage());
    }



    @PostMapping("/add")    //新增患者
    public JsonVO<String> add(@RequestBody PatientAlterDTO patientAlterDTO){
        Result<String> result = patientInformationService.patientAdd(patientAlterDTO);
        if(result.isStatus())return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    @PostMapping("/adds")
    public JsonVO<String> adds(@RequestBody List<PatientAlterDTO> patientList) {
        try {
            // 批量导入患者信息
            Result<String> result = patientInformationService.patientsAdd(patientList);
            if (result.isStatus()) return JsonVO.success(result.getMessage());
            return JsonVO.fail(result.getMessage());
        } catch (Exception e) {
            return JsonVO.fail("导入失败：" + e.getMessage());
        }
    }



    @PostMapping ("/edit")    //编辑患者
    public JsonVO<String> edit(@RequestBody PatientEditDTO patientEditDTO){
        Result<String> result = patientInformationService.patientEdit(patientEditDTO);
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
