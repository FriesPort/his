package com.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.systemmanagement.userrole.UserCreateDTO;
import com.example.dto.systemmanagement.userrole.UserRoleDeleteDTO;
import com.example.dto.systemmanagement.userrole.UserRolePermissionSearchDTO;
import com.example.dto.systemmanagement.userrole.UserRoleUpdateDTO;
import com.example.service.IUserRolesService;
import com.example.vo.systemmanagement.userrole.UserCreateVO;
import com.example.vo.systemmanagement.userrole.UserRoleDeleteVO;
import com.example.vo.systemmanagement.userrole.UserRolePermissionSearchVO;
import com.example.vo.systemmanagement.userrole.UserRoleUpdateVO;
import com.example.vo.systemmanagement.users.UsersDisplayVO;
import com.example.dto.systemmanagement.users.UserAddDTO;
import com.example.dto.systemmanagement.users.UserDeleteDTO;
import com.example.dto.systemmanagement.users.UserDisplayDTO;
import com.example.dto.systemmanagement.users.UserUpdateDTO;
import com.example.service.IUserService;
import com.example.vo.JsonVO;
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
@RequestMapping("/user")
public class UsersController{
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IUserRolesService iUserRolesService;




    @PostMapping("/msg/add")
    public JsonVO<String> AddUser(@RequestBody UserAddDTO userAddDTO) {
        if(iUserService.insertUser(userAddDTO)){
            return JsonVO.success("Add User Message Successful");
        }else {
            return JsonVO.fail("Fail to Delete User Message");
        }
    }

    @PostMapping("/msg/delete")
    public JsonVO<String> DeleteUser(UserDeleteDTO userDeleteDTO) {
        if(iUserService.deleteUser(userDeleteDTO)){
            return JsonVO.success("Delete User Message Successful");
        }else {
            return JsonVO.fail("Fail To Delete User Message");
        }
    }

    @PostMapping("/msg/update")
    public JsonVO<String> UpdateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        if(iUserService.updateUser(userUpdateDTO)){
            return JsonVO.success("update success");
        }else {
            return JsonVO.fail("fail to update user");
        }
    }


    //todo
    @GetMapping("/msg/display")
    public JsonVO<IPage<UserDisplayDTO>> UserList(UserDisplayDTO userDisplayDTO, @RequestParam long current, @RequestParam long size) {
        Page<UsersDisplayVO> page = new Page<>(current, size);
        return JsonVO.success(iUserService.userlist(userDisplayDTO,page));
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

    @PostMapping("/roleandpermission/search")
    public JsonVO<List<UserRolePermissionSearchVO>> U_R_P_search(@RequestBody UserRolePermissionSearchDTO userRolePermissionSearchDTO) {
        return JsonVO.success(iUserRolesService.all_search(userRolePermissionSearchDTO));
    }



}

