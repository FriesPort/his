package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.systemmanagement.roles.RoleCreateDTO;
import com.example.entity.Permission;
import com.example.entity.Role;
import com.example.entity.RolePermission;
import com.example.mapper.PermissionMapper;
import com.example.mapper.RolePermissionMapper;
import com.example.mapper.RoleMapper;
import com.example.service.IRolePermissionService;
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
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

    @Autowired
    RolePermissionMapper rolePermissionMapper;
    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    IdGenerate idGenerate;

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
}
