package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.systemmanagement.roles.RoleCreateDTO;
import com.example.entity.Permissions;
import com.example.entity.RolePermissions;
import com.example.entity.Role;
import com.example.mapper.PermissionsMapper;
import com.example.mapper.RolePermissionsMapper;
import com.example.mapper.RoleMapper;
import com.example.service.IRolePermissionsService;
import com.example.utils.IdGenerate;
import com.example.vo.systemmanagement.roles.RoleCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author diandian
 * @since 2024-04-17
 */
@Service
public class RolePermissionsServiceImpl extends ServiceImpl<RolePermissionsMapper, RolePermissions> implements IRolePermissionsService {

    @Autowired
    RolePermissionsMapper rolePermissionsMapper;
    @Autowired
    PermissionsMapper permissionsMapper;

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    IdGenerate idGenerate;

    Role role =new Role();
    RolePermissions rolePermissions=new RolePermissions();



    @Override
    public RoleCreateVO definedRole(RoleCreateDTO roleCreateDTO) {
        role.setId(idGenerate.nextUUID(roleCreateDTO));
        role.setName(roleCreateDTO.getRoleName());
        role.setDescription(roleCreateDTO.getDescription());
        roleMapper.insert(role);
        List<String> permissions=roleCreateDTO.getPermissions();
        for(String name:permissions){
            Permissions p=permissionsMapper
                    .selectOne(new QueryWrapper<Permissions>().eq("permission_name",name));
            rolePermissions.setRpId(idGenerate.nextUUID(role.getId()+p.getPermissionId()));
            rolePermissions.setPermissionId(p.getPermissionId());
            rolePermissions.setPermissionId(role.getId());
            rolePermissionsMapper.insert(rolePermissions);
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
}
