package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.systemmanagement.roles.RoleCreateDTO;
import com.example.entity.Permissions;
import com.example.entity.RolePermissions;
import com.example.entity.Roles;
import com.example.mapper.PermissionsMapper;
import com.example.mapper.RolePermissionsMapper;
import com.example.mapper.RolesMapper;
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
    RolesMapper rolesMapper;
    @Autowired
    IdGenerate idGenerate;

    Roles roles=new Roles();
    RolePermissions rolePermissions=new RolePermissions();



    @Override
    public RoleCreateVO definedRole(RoleCreateDTO roleCreateDTO) {
        roles.setRoleId(idGenerate.nextUUID(roleCreateDTO));
        roles.setRoleName(roleCreateDTO.getRoleName());
        roles.setDescription(roleCreateDTO.getDescription());
        rolesMapper.insert(roles);
        List<String> permissions=roleCreateDTO.getPermissions();
        for(String name:permissions){
            Permissions p=permissionsMapper
                    .selectOne(new QueryWrapper<Permissions>().eq("permission_name",name));
            rolePermissions.setRpId(idGenerate.nextUUID(roles.getRoleId()+p.getPermissionId()));
            rolePermissions.setPermissionId(p.getPermissionId());
            rolePermissions.setRoleId(roles.getRoleId());
            rolePermissionsMapper.insert(rolePermissions);
        }

        RoleCreateVO roleCreateVO=new RoleCreateVO();
        roleCreateVO.setDescription(String.valueOf(rolesMapper
                .selectOne(new QueryWrapper<Roles>()
                        .eq("role_id",roles
                                .getRoleId())).getDescription()));
        roleCreateVO.setRoleName(String.valueOf(rolesMapper
                .selectOne(new QueryWrapper<Roles>()
                        .eq("role_id",roles.getRoleId())).getRoleName()));
        roleCreateVO.setRoleId(roles.getRoleId());
        roleCreateVO.setPermissionsName(roleCreateDTO.getPermissions());
        return roleCreateVO;
    }
}
