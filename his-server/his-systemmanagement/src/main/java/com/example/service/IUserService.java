package com.example.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.systemmanagement.users.UserDisplayDTO;
import com.example.dto.systemmanagement.users.UserAddDTO;
import com.example.dto.systemmanagement.users.UserDeleteDTO;
import com.example.dto.systemmanagement.users.UserUpdateDTO;
import com.example.entity.User;
import com.example.vo.systemmanagement.users.UsersDisplayVO;

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

    boolean deleteUser(UserDeleteDTO userDeleteDTO);

}
