package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.systemmanagement.rolepermission.RoleAllocationDTO;
import com.example.dto.systemmanagement.roles.RoleCreateDTO;
import com.example.entity.RolePermission;
import com.example.vo.systemmanagement.role.PermissionDisplayVO;
import com.example.vo.systemmanagement.role.RoleCreateVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author diandian
 * @since 2024-04-17
 */
public interface IRolePermissionService extends IService<RolePermission> {
    //自定义角色
    RoleCreateVO definedRole(RoleCreateDTO roleCreateDTO, String userId);

    //角色权限展示
    PermissionDisplayVO permissionDisplay(String roleId);

    //角色权限分配
    boolean roleAllocation(RoleAllocationDTO allocationDTO,String userId);
}
