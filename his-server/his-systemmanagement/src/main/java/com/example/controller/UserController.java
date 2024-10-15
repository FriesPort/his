package com.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.login.LoginDTO;
import com.example.dto.systemmanagement.userrole.UserCreateDTO;
import com.example.dto.systemmanagement.userrole.UserRoleDeleteDTO;
import com.example.dto.systemmanagement.userrole.UserRolePermissionSearchDTO;
import com.example.dto.systemmanagement.userrole.UserRoleUpdateDTO;
import com.example.dto.systemmanagement.user.*;
import com.example.service.IUserRolesService;
import com.example.service.IUserService;
import com.example.service.LoginService;
import com.example.vo.JsonVO;
import com.example.vo.login.LoginVO;
import com.example.vo.systemmanagement.userrole.*;
import com.example.vo.systemmanagement.user.UserDisplayVO;
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
@RequestMapping("system/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private LoginService loginService;

    /**
     * 添加用户
     * @param userAddDTO
     * @return JsonVO<String>
     */
    @PutMapping("/msg/add")
    public JsonVO<String> AddUser(@RequestBody UserAddDTO userAddDTO) {
        if(iUserService.insertUser(userAddDTO)){
            return JsonVO.success("Add User Message Successful");
        }else {
            return JsonVO.fail("Fail to Delete User Message");
        }
    }

    /**
     * 删除用户
     * @param id
     * @return JsonVO<String>
     */
    @DeleteMapping ("/msg/delete")
    public JsonVO<String> DeleteUser(@RequestParam String id) {
        if(iUserService.deleteUser(id)){
            return JsonVO.success("Delete User Message Successful");
        }else {
            return JsonVO.fail("Fail To Delete User Message");
        }
    }

    /**
     * 修改用户
     * @param userUpdateDTO
     * @return JsonVO<String>
     */
    @PostMapping("/msg/update")
    public JsonVO<String> UpdateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        if(iUserService.updateUser(userUpdateDTO)){
            return JsonVO.success("update success");
        }else {
            return JsonVO.fail("fail to update user");
        }
    }

    /**
     * 展示用户列表
     * @param userDisplayDTO
     * @param current
     * @param size
     * @return JsonVO<IPage<UserDisplayDTO>>
     */
    @GetMapping("/msg/display")
    public JsonVO<IPage<UserDisplayDTO>> UserList(UserDisplayDTO userDisplayDTO, @RequestParam long current, @RequestParam long size) {
        Page<UserDisplayVO> page = new Page<>(current, size);
        if(page!=null){
            return JsonVO.success(iUserService.userlist(userDisplayDTO,page));
        }else {
            return JsonVO.fail(null);
        }
    }

    /**
     * 向用户分配角色
     * @param userCreateDTO
     * @return JsonVO<String>
     */
    @PutMapping("/role/allocation")
    public JsonVO<String> UserRoleAllocation(@RequestBody UserCreateDTO userCreateDTO) {
        if(iUserService.allocateRole(userCreateDTO)){
            return JsonVO.success("Allocate role success");
        }else {
            return JsonVO.fail("Fail to allocate role");
        }
    }

    /**
     * 移除用户的角色
     * @param userId
     * @param roleId
     * @return JsonVO<String>
     */
    @DeleteMapping("/role/delete")
    public JsonVO<String> UserRoleDelete(@RequestParam String userId,@RequestParam String roleId) {
        if(iUserService.userRoleDelete(userId,roleId)){
            return JsonVO.success(UserRoleDeleteVO.success);
        }else{
            return JsonVO.fail(UserRoleDeleteVO.fail);
        }
    }

    /**
     * 获取用户所拥有的角色
     * @param userId
     * @return JsonVO<List<UserRoleDisplayVo>>
     */
    @GetMapping("/role/getOwnedRole")
    public JsonVO<List<UserRoleDisplayVo>> OwnedUserRoleDisplay(@RequestParam String userId){
        List<UserRoleDisplayVo> userRoleDisplayVoList = iUserService.userRoleDisplay(userId);
        if(userRoleDisplayVoList!= null){
            return JsonVO.success(userRoleDisplayVoList);
        }else {
            return JsonVO.fail(null);
        }
    }

    /**
     * 获取用户未拥有的角色
     * @param userId
     * @return JsonVO<List<UserRoleDisplayVo>>
     */
    @GetMapping("/role/getUnownedRole")
    public JsonVO<List<UserRoleDisplayVo>> UnownedUserRoleDisplay(@RequestParam String userId){
        List<UserRoleDisplayVo> userRoleDisplayVoList = iUserService.unownedUserRoleDisplay(userId);
        if(userRoleDisplayVoList!= null){
            return JsonVO.success(userRoleDisplayVoList);
        }else {
            return JsonVO.fail(null);
        }
    }

//    @PutMapping("/role/update")
//    public JsonVO<String> UserPermissionUpdate(@RequestBody List<UserRoleUpdateDTO> userRoleUpdateDTO) {
//        if ((iUserRolesService.userperupdate(userRoleUpdateDTO))){
//            return JsonVO.success(UserRoleUpdateVO.success);
//        }else {
//            return JsonVO.fail(UserRoleUpdateVO.fail);
//        }
//    }

//    @PostMapping("/role/permissionSearch")
//    public JsonVO<List<UserRolePermissionSearchVO>> U_R_P_search(UserRolePermissionSearchDTO userRolePermissionSearchDTO) {
//        return JsonVO.success(iUserRolesService.all_search(userRolePermissionSearchDTO));
//    }

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

