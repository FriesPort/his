package com.example.controller;


import com.example.dto.login.LoginDTO;
import com.example.dto.systemmanagement.userrole.UserCreateDTO;
import com.example.dto.systemmanagement.userrole.UserRoleDeleteDTO;
import com.example.dto.systemmanagement.userrole.UserRolePermissionSearchDTO;
import com.example.dto.systemmanagement.userrole.UserRoleUpdateDTO;
import com.example.dto.systemmanagement.users.*;
import com.example.service.IUserRolesService;
import com.example.service.IUsersService;
import com.example.service.LoginService;
import com.example.vo.JsonVO;
import com.example.vo.login.LoginVO;
import com.example.vo.systemmanagement.userrole.UserCreateVO;
import com.example.vo.systemmanagement.userrole.UserRoleDeleteVO;
import com.example.vo.systemmanagement.userrole.UserRolePermissionSearchVO;
import com.example.vo.systemmanagement.userrole.UserRoleUpdateVO;
import com.example.vo.systemmanagement.users.UsersAddVO;
import com.example.vo.systemmanagement.users.UsersDisplayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
@RestController
@RequestMapping("/users")
public class UsersController{
    @Autowired
    IUsersService iUsersService;

    @Autowired
    IUserRolesService iUserRolesService;
    @Autowired
    LoginService loginService;

    /**
     * 把UserCreate的功能拆分，useradd负责添加用户，usercreate负责添加用户权限
     * @param usersAddDTO
     * @return
     */

    @PostMapping("/msg/add")
    public JsonVO<Object> AddUser(@RequestBody UsersAddDTO usersAddDTO) {
        UsersAddVO vo=iUsersService.insertUser(usersAddDTO);
        if(vo!=null){
            return JsonVO.success(vo);
        }else{
            return JsonVO.fail(vo);
        }

    }




    @DeleteMapping("/msg/delete")
    public JsonVO<Object> DeleteUser(UsersDeleteDTO usersDeleteDTO) {
        if(iUsersService.DeleteUser(usersDeleteDTO)){
            return JsonVO.success("Delete User Message Successful");
        }else {
            return JsonVO.fail("Fail To Delete User Message");
        }
    }


    @PutMapping("/msg/update")
    public JsonVO<Object> UpdateUser(@RequestBody UsersUpdateDTO usersUpdateDTO) {
        if(iUsersService.UpdateUser(usersUpdateDTO)){
            return JsonVO.success("update success");
        }else {
            return JsonVO.fail("fail to update user");
        }
    }


    public JsonVO<Object> SearchUser(UsersSearchDTO usersSearchDTO) {
        return null;
    }


    @GetMapping("/msg/display")
    public JsonVO<List<UsersDisplayVO>> UserList(UserDisplayDTO userDisplayDTO) {
        return JsonVO.success(iUsersService.userlist(userDisplayDTO));
    }


    @PostMapping("/role/allocation")
    public JsonVO<Object> UserCreate(@RequestBody UserCreateDTO userCreateDTO) {
        UserCreateVO userCreateVO=iUserRolesService.defineUser(userCreateDTO);
        if(userCreateVO!=null){
            return JsonVO.success(userCreateVO);
        }else {
            return JsonVO.fail("This role has exited!!!");
        }

    }

    @DeleteMapping("/role/delete")
    public JsonVO<String> UserPermissionDelete(@ModelAttribute UserRoleDeleteDTO userRoleDeleteDTO) {
        if(iUserRolesService.userperdelete(userRoleDeleteDTO)){
            return JsonVO.success(UserRoleDeleteVO.success);
        }else{
            return JsonVO.fail(UserRoleDeleteVO.fail);
        }
    }


    @PutMapping("/role/update")
    public JsonVO<String> UserPermissionUpdate(@RequestBody List<UserRoleUpdateDTO> userRoleUpdateDTO) {
        if ((iUserRolesService.userperupdate(userRoleUpdateDTO))){
            return JsonVO.success(UserRoleUpdateVO.success);
        }else {
            return JsonVO.fail(UserRoleUpdateVO.fail);
        }
    }

    @GetMapping("/roleandpermission/search")
    public JsonVO<List<UserRolePermissionSearchVO>> U_R_P_search(UserRolePermissionSearchDTO userRolePermissionSearchDTO) {
        return JsonVO.success(iUserRolesService.all_search(userRolePermissionSearchDTO));
    }


    @PostMapping("/login")
    public JsonVO<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        LoginVO vo=loginService.login(loginDTO);

        try{
            if (vo.getToken() == null) {
                return JsonVO.fail(vo);
            } else if (vo.getLoginstatus().equals("账号或密码错误")) {
                return JsonVO.fail(vo);
            }
        }catch (Exception e){
            return JsonVO.fail(vo);
        }
        return JsonVO.success(vo);
    }


}

