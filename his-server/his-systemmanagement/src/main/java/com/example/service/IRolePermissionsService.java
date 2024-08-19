package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.systemmanagement.roles.RoleCreateDTO;
import com.example.entity.RolePermissions;
import com.example.vo.systemmanagement.roles.RoleCreateVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author diandian
 * @since 2024-04-17
 */
public interface IRolePermissionsService extends IService<RolePermissions> {
    RoleCreateVO definedRole(RoleCreateDTO roleCreateDTO);
}
