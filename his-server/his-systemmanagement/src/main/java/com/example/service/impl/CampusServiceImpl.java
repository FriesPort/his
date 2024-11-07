package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.example.dto.systemmanagement.campus.CampusUpdateDTO;
import com.example.entity.Campus;
import com.example.entity.Hospital;
import com.example.mapper.CampusMapper;
import com.example.service.ICampusService;
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
 * @since 2024-11-05
 */
@Service
public class CampusServiceImpl extends ServiceImpl<CampusMapper, Campus> implements ICampusService {

    @Autowired
    IdGenerate idGenerate;
    @Override
    public Boolean add(Campus campus, String userId) {
        if(!Db.lambdaQuery(Hospital.class).eq(Hospital::getId,campus.getHospitalId()).exists()){
            return false;
        }
        campus.setId(idGenerate.nextUUID(campus));
        campus.setCreateBy(userId);
        campus.setCreateTime(LocalDateTime.now());
        return baseMapper.insert(campus)>0;
    }

    @Override
    public Boolean update(CampusUpdateDTO dto, String userId) {
        Campus campus=new Campus();
        Map<String,String> map=dto.getCampus();
        Class<?> clazz=campus.getClass();
        for(Map.Entry<String,String> entry:map.entrySet()){
            try{
                Field field=clazz.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(campus,entry.getValue());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        campus.setId(dto.getId());
        campus.setUpdateBy(userId);
        campus.setUpdateTime(LocalDateTime.now());
        try{
            baseMapper.updateById(campus);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
