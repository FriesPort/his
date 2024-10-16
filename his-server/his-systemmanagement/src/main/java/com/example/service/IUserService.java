package com.example.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.systemmanagement.user.UserDisplayDTO;
import com.example.dto.systemmanagement.user.UserAddDTO;
import com.example.dto.systemmanagement.user.UserDeleteDTO;
import com.example.dto.systemmanagement.user.UserUpdateDTO;
import com.example.dto.systemmanagement.userrole.UserCreateDTO;
import com.example.entity.User;
import com.example.vo.systemmanagement.userrole.UserRoleDisplayVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
public interface IUserService extends IService<User> {
    Boolean insertUser(UserAddDTO userAddDTO);

    IPage<UserDisplayDTO> userlist(UserDisplayDTO userDisplayDTO, Page page);

    boolean updateUser(UserUpdateDTO userUpdateDTO);

    boolean deleteUser(String id);

    boolean allocateRole(UserCreateDTO userCreateDTO);


    boolean userRoleDelete(String userId, String roleId);

    List<UserRoleDisplayVo> userRoleDisplay(String userId);

    List<UserRoleDisplayVo> unownedUserRoleDisplay(String userId);
}
