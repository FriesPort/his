package com.example.controller;

import com.example.dto.patientChangeBedDTO;
import com.example.dto.patientInformationDTO;
import com.example.pojo.patientInformation;
import com.example.service.AssignBedService;
import com.example.vo.JsonVO;
import com.example.vo.Result;
import com.example.vo.getOnBed.getOnBedVo;
import com.example.vo.getOutBed.getOutBedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Assign bed controller.
 */
@RestController
@RequestMapping("/assign")
public class AssignBedController {

    @Autowired
    private AssignBedService assignBedService;

    /**
     * Inform json vo.
     *
     * @param id the id
     * @return the json vo
     */
//患者通知
    //更改bed表中的 is_null(在预分配中实现)
    @GetMapping("/inform")
    public JsonVO<String> inform(@RequestParam String id) {
        Result<String> result = assignBedService.inform(id);
        if(result.isStatus())return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    /**
     * In hospital json vo.
     *
     * @param patientInformationDTO the patient information dto
     * @return the json vo
     */
//患者入院
    @PostMapping("/inHospital")
    public JsonVO<String> inHospital(@RequestBody patientInformationDTO patientInformationDTO){
        Result<String> result = assignBedService.inHospital(patientInformationDTO);
        if(result.isStatus())return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    /**
     * Discharge json vo.
     *
     * @param patientInformationDTO the patient information dto
     * @return the json vo
     */
//患者出院
    //bed表 patient_id置为0  is_null置为0 update_by  update_time
    //patientInformation表 is_inhospital置为0  bed_id置为0  update_by  update_time
    @PostMapping("/discharge")
    public JsonVO<String> discharge(@RequestBody patientInformationDTO patientInformationDTO){
        Result<String> result = assignBedService.discharge(patientInformationDTO);
        if(result.isStatus())return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    /**
     * Preassign json vo.
     *
     * @param patientInformationDTO the patient information dto
     * @return the json vo
     */
//预分配床位
    //bed表  patient_id  is_null   update_by  update_time
    //patientInformation表  bed_id  update_by  update_time
    @PostMapping("/preassign")
    public JsonVO<String> preassign(@RequestBody patientInformationDTO patientInformationDTO){
        Result<String> result = assignBedService.preassign(patientInformationDTO);
        if(result.isStatus())return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }

    /**
     * Get out bed json vo.
     *
     * @return the json vo
     */
//传入空床位
    @GetMapping("/getOutBed")
    public JsonVO<List<getOutBedVo>> getOutBed(){
        List<getOutBedVo> list = assignBedService.getOutBed();
        for (getOutBedVo getOutBedVo : list) {
            System.out.println(getOutBedVo);
        }
        return JsonVO.success(list);
    }

    /**
     * Get on bed json vo.
     *
     * @return the json vo
     */
//传入占用床位
    @GetMapping("/getOnBed")
    public JsonVO<List<getOnBedVo>> getOnBed(){
        List<getOnBedVo> list = assignBedService.getOnBed();
        for (getOnBedVo getOnBedVo : list) {

            System.out.println(getOnBedVo);
        }
        return JsonVO.success(list);
    }

    /**
     * Change bed json vo.
     *
     * @param patientChangeBedDTO the patient change bed dto
     * @return the json vo
     */
//患者更改床位
    @PostMapping("/changeBed")
    public JsonVO<String> changeBed(@RequestBody patientChangeBedDTO patientChangeBedDTO){
        Result<String> result = assignBedService.changeBed(patientChangeBedDTO);
        if(result.isStatus())return JsonVO.success(result.getMessage());
        return JsonVO.fail(result.getMessage());
    }
}
