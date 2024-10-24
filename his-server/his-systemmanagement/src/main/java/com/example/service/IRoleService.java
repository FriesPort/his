package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Role;
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
public interface IRoleService extends IService<Role> {
    public List<RoleListVO> rolelist();
}
