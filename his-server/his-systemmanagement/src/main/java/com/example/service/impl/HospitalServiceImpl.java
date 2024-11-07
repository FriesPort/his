package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.systemmanagement.hospital.HospitalUpdateDTO;
import com.example.entity.Hospital;
import com.example.mapper.HospitalMapper;
import com.example.service.IHospitalService;
import com.example.utils.IdGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author diandian
 * @since 2024-11-06
 */
@Service
public class HospitalServiceImpl extends ServiceImpl<HospitalMapper, Hospital> implements IHospitalService {

    @Autowired
    private IdGenerate idGenerate;
    @Override
    public Boolean add(Hospital hospital,String userId) {
        hospital.setId(idGenerate.nextUUID(hospital));
        hospital.setCreateBy(userId);
        hospital.setCreateTime(LocalDateTime.now());
        int i = baseMapper.insert(hospital);
        return i > 0;
    }

    @Override
    public Boolean update(HospitalUpdateDTO dto, String userId) {
        Hospital hospital=new Hospital();
        Map<String,String> map=dto.getHospital();
        Class<?> clazz=hospital.getClass();
        for(Map.Entry<String,String> entry:map.entrySet()){
            try{
                Field field=clazz.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(hospital,entry.getValue());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        hospital.setUpdateBy(userId);
        hospital.setUpdateTime(LocalDateTime.now());
        hospital.setId(dto.getId());
        try{
            baseMapper.updateById(hospital);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
