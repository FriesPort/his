package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dto.patientChangeBedDTO;
import com.example.pojo.patientInformation;
import com.example.vo.Result;
import com.example.vo.getOnBed.getOnBedVo;
import com.example.vo.getOutBed.getOutBedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssignBedMapper extends BaseMapper<patientInformation> {
    //通过mybatis的方式获取bed_id字段的值
    @Select("select bed_id from patient_information where id=#{id}")
    public String getBedId(String id);

    //获取空床位，并映射到getOutBedVo中
    public List<getOutBedVo> selectOutBed();

    //获取占用床位，并映射到getOnBedVo
    public List<getOnBedVo> selectOnBed();

    //更改床位——更改床位表
    public void changeBed(patientChangeBedDTO patientChangeBedDTO);
    //更改床位——更改患者表
    public void changePatient(patientChangeBedDTO patientChangeBedDTO);
}
