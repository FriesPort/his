package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
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
public interface IPermissionsService extends IService<Permissions> {
    List<PermissionListVO> permissionList();
}
