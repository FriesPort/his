package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Roles;
import com.example.vo.systemmanagement.roles.RoleListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
public interface IRolesService extends IService<Roles> {
    public List<RoleListVO> rolelist();
}
