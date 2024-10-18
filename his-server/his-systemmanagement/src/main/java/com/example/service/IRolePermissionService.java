package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.systemmanagement.roles.RoleCreateDTO;
import com.example.entity.RolePermission;
import com.example.vo.systemmanagement.roles.RoleCreateVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author diandian
 * @since 2024-04-17
 */
public interface IRolePermissionService extends IService<RolePermission> {
    RoleCreateVO definedRole(RoleCreateDTO roleCreateDTO);
}
