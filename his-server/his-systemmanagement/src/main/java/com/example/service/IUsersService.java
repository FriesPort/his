package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.systemmanagement.users.UsersAddDTO;
import com.example.dto.systemmanagement.users.UsersDeleteDTO;
import com.example.vo.systemmanagement.users.UsersDisplayVO;
import com.example.dto.systemmanagement.users.UserDisplayDTO;
import com.example.dto.systemmanagement.users.UsersUpdateDTO;
import com.example.vo.systemmanagement.users.UsersAddVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
public interface IUsersService extends IService<Users> {
    UsersAddVO insertUser(UsersAddDTO usersAddDTO);

    List<UsersDisplayVO> userlist(UserDisplayDTO userDisplayDTO);

    boolean UpdateUser(UsersUpdateDTO usersUpdateDTO);

    boolean DeleteUser(UsersDeleteDTO usersDeleteDTO);

}
