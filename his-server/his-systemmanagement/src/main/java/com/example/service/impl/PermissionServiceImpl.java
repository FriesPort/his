package com.example.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.systemmanagement.permission.DataRuleDTO;
import com.example.dto.systemmanagement.permission.PermissionRegisterDTO;
import com.example.dto.systemmanagement.permission.PermissionUpdateDTO;
import com.example.entity.Permission;
import com.example.entity.PermissionDataRule;
import com.example.mapper.PermissionDataRuleMapper;
import com.example.mapper.PermissionMapper;
import com.example.service.IPermissionService;
import com.example.utils.IdGenerate;
import com.example.vo.systemmanagement.permissions.PermissionListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
@Slf4j
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    PermissionDataRuleMapper permissionDataRuleMapper;
    @Autowired
    IdGenerate idGenerate;
    @Override
    public List<PermissionListVO> permissionList(String permissionName) {
        List<Permission> permissionList=new ArrayList<>();
        List<PermissionDataRule> dataRuleList=new ArrayList<>();
        if(StrUtil.isEmpty(permissionName)) {
            permissionList= permissionMapper.selectList(null);
            dataRuleList=permissionDataRuleMapper.selectList(null);
        }else{
            permissionList= permissionMapper.selectList(new LambdaQueryWrapper<Permission>().eq(Permission::getName,permissionName));
            String id=permissionList.get(0).getId();
            dataRuleList=permissionDataRuleMapper.selectList(new LambdaQueryWrapper<PermissionDataRule>().eq(PermissionDataRule::getPermissionId,id));
        }

        if(permissionList==null){
            return null;
        }
        List<PermissionListVO> permissionListVOList=new ArrayList<>();
        for(Permission permission:permissionList){
            PermissionListVO permissionListVO=new PermissionListVO();
            List<Object> rule=new ArrayList<>();
            BeanUtils.copyProperties(permission,permissionListVO);
            for(PermissionDataRule dataRule:dataRuleList){
                if(permission.getId().equals(dataRule.getPermissionId())){
                    rule.add(dataRule);
                }
            }
            permissionListVO.setDataRuleList(rule);
            permissionListVOList.add(permissionListVO);
        }
        return permissionListVOList;

    }

    @Override
    public Boolean UpdatePermission(PermissionUpdateDTO dto,String userId) {
        Permission permission=new Permission();
        Map<String,String> map=dto.getPermission();
        Class<?> clazz=permission.getClass();
        for(Map.Entry<String,String> entry:map.entrySet()){
            try{
                Field field=clazz.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(permission,entry.getValue());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        permission.setId(dto.getPermissionId());
        permission.setUpdateTime(LocalDateTime.now());
        permission.setUpdateBy(userId);
        try{
            int i=permissionMapper.updateById(permission);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean RegisterPermission(PermissionRegisterDTO registerDTO,String userId) {
        Permission permission=new Permission();
        BeanUtils.copyProperties(registerDTO,permission);
        permission.setId("idGenerate.nextUUID(registerDTO)");
        permission.setCreateTime(LocalDateTime.now());
        permission.setCreateBy(userId);
        int i=permissionMapper.insert(permission);
        if(i!=1){
            log.info("权限数据插入错误{}",i);
            return false;
        }
        if(registerDTO.getIsDatarule()==1){
            PermissionDataRule dataRule=new PermissionDataRule();
            BeanUtils.copyProperties(registerDTO,dataRule);
            dataRule.setId(idGenerate.nextUUID(dataRule));
            dataRule.setPermissionId(permission.getId());
            dataRule.setCreateBy(userId);
            dataRule.setCreateTime(LocalDateTime.now());
            int j=permissionDataRuleMapper.insert(dataRule);
            if(j!=1){
                log.info("数据规则插入错误{}",j);
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean DataRuleInsert(DataRuleDTO dto, String userId) {
        PermissionDataRule dataRule=new PermissionDataRule();
        BeanUtils.copyProperties(dto,dataRule);
        dataRule.setId(idGenerate.nextUUID(dataRule));
        dataRule.setCreateTime(LocalDateTime.now());
        dataRule.setCreateBy(userId);
        if(permissionDataRuleMapper.insert(dataRule)>=1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean DataRuleUpdate(DataRuleDTO dto, String userId) {
        PermissionDataRule dataRule=new PermissionDataRule();
        BeanUtils.copyProperties(dto,dataRule);
        dataRule.setUpdateTime(LocalDateTime.now());
        dataRule.setUpdateBy(userId);
        if(permissionDataRuleMapper.updateById(dataRule)>=1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean DataRuleDelete(String id) {
        if(permissionDataRuleMapper.deleteById(id)>=1){
            return true;
        }else {
            return false;
        }
    }
}
