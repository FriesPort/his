package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.systemmanagement.rolepermission.RoleAllocationDTO;
import com.example.dto.systemmanagement.roles.RoleCreateDTO;
import com.example.entity.*;
import com.example.mapper.PermissionDataRuleMapper;
import com.example.mapper.PermissionMapper;
import com.example.mapper.RolePermissionMapper;
import com.example.mapper.RoleMapper;
import com.example.service.IRolePermissionService;
import com.example.utils.IdGenerate;
import com.example.utils.RedisCache;
import com.example.vo.systemmanagement.role.PermissionDisplayVO;
import com.example.vo.systemmanagement.role.RoleCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author diandian
 * @since 2024-04-17
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

    @Autowired
    RolePermissionMapper rolePermissionMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    PermissionDataRuleMapper permissionDataRuleMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    IdGenerate idGenerate;
    @Autowired
    RedisCache redisCache;
    Role role =new Role();
    RolePermission rolePermission=new RolePermission();



    @Override
    public RoleCreateVO definedRole(RoleCreateDTO roleCreateDTO) {
        role.setId(idGenerate.nextUUID(roleCreateDTO));
        role.setName(roleCreateDTO.getRoleName());
        role.setDescription(roleCreateDTO.getDescription());
        roleMapper.insert(role);
        List<String> permissions=roleCreateDTO.getPermissions();
        for(String name:permissions){
            Permission p= permissionMapper
                    .selectOne(new QueryWrapper<Permission>().eq("permission_name",name));
            rolePermission.setId(idGenerate.nextUUID(role.getId()+p.getId()));
            rolePermission.setPermissionId(p.getId());
            rolePermission.setPermissionId(role.getId());
            rolePermissionMapper.insert(rolePermission);
        }

        RoleCreateVO roleCreateVO=new RoleCreateVO();
        roleCreateVO.setDescription(String.valueOf(roleMapper
                .selectOne(new QueryWrapper<Role>()
                        .eq("role_id", role
                                .getId())).getDescription()));
        roleCreateVO.setRoleName(String.valueOf(roleMapper
                .selectOne(new QueryWrapper<Role>()
                        .eq("role_id", role.getId())).getName()));
//        roleCreateVO.setPermissionsName(role.getName());
        roleCreateVO.setPermissionsName(roleCreateDTO.getPermissions());
        return roleCreateVO;
    }

    @Override
    public List<PermissionDisplayVO> permissionDisplay(String roleId) {
        List<Permission> permissions=permissionMapper.SearchListById(roleId);
        List<PermissionDisplayVO> voList=new ArrayList<>();
        for(Permission permission:permissions){
            if(permission.getIsDatarule()==1){
                PermissionDataRule permissionDataRule=permissionDataRuleMapper
                        .selectOne(new LambdaQueryWrapper<PermissionDataRule>()
                                .eq(PermissionDataRule::getPermissionId,permission
                                        .getId()));
                Map<String,String> map=new HashMap<>();
                map.put("rule_name",permissionDataRule.getRuleName());
                map.put("rule_column",permissionDataRule.getRuleColumn());
                map.put("rule_conditions",permissionDataRule.getRuleConditions());
                map.put("rule_value",permissionDataRule.getRuleValue());
                voList.add(
                        new PermissionDisplayVO(
                                permission.getName(),
                                permission.getDescription(),
                                map));
            }else {
                voList.add(new PermissionDisplayVO(permission.getName(),permission.getDescription(),null));
            }
        }
        return voList;
    }

    @Override
    public boolean roleAllocation(RoleAllocationDTO allocationDTO,String userId) {
        try{
            for(String permission:allocationDTO.getAdd()){
                rolePermissionMapper.insert(new RolePermission(
                        idGenerate.nextUUID(allocationDTO),
                        allocationDTO.getRoleId(),
                        permission,
                        LocalDateTime.now(),
                        userId,
                        null,
                        null));
            }
            for(String permission:allocationDTO.getDel()){
                rolePermissionMapper.delete(new LambdaQueryWrapper<RolePermission>()
                        .eq(RolePermission::getPermissionId,permission)
                        .eq(RolePermission::getRoleId,allocationDTO.getRoleId()));
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
