package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.systemmanagement.userrole.UserCreateDTO;
import com.example.dto.systemmanagement.userrole.UserRoleDeleteDTO;
import com.example.dto.systemmanagement.userrole.UserRolePermissionSearchDTO;
import com.example.dto.systemmanagement.userrole.UserRoleUpdateDTO;
import com.example.vo.systemmanagement.userrole.UserCreateVO;
import com.example.vo.systemmanagement.userrole.UserRolePermissionSearchVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author diandian
 * @since 2024-04-17
 */
public interface IUserRolesService extends IService<UserRoles> {
    UserCreateVO defineUser(UserCreateDTO userCreateDTO);

    Boolean userperdelete(UserRoleDeleteDTO userRoleDeleteDTO);

    Boolean userperupdate(List<UserRoleUpdateDTO> userRoleUpdateDTO);

    List<UserRolePermissionSearchVO> all_search(UserRolePermissionSearchDTO userRolePermissionSearchDTO);
}
