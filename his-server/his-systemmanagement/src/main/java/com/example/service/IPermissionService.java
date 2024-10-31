package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.systemmanagement.permission.DataRuleDTO;
import com.example.dto.systemmanagement.permission.PermissionRegisterDTO;
import com.example.dto.systemmanagement.permission.PermissionUpdateDTO;
import com.example.entity.Permission;
import com.example.vo.systemmanagement.permissions.PermissionListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
public interface IPermissionService extends IService<Permission> {
    List<PermissionListVO> permissionList(String permissionName);

    Boolean RegisterPermission(PermissionRegisterDTO registerDTO,String userId);

    Boolean UpdatePermission(PermissionUpdateDTO dto,String userId);

    Boolean DeletePermission(String id);

    Boolean DataRuleInsert(DataRuleDTO dto, String userId);

    Boolean DataRuleUpdate(DataRuleDTO dto, String userId);

    Boolean DataRuleDelete(String id);
}
