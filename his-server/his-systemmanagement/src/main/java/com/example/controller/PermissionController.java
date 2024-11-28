package com.example.controller;


import com.example.dto.systemmanagement.permission.DataRuleDTO;
import com.example.dto.systemmanagement.permission.PermissionRegisterDTO;
import com.example.dto.systemmanagement.permission.PermissionUpdateDTO;
import com.example.service.IPermissionService;
import com.example.vo.JsonVO;
import com.example.vo.systemmanagement.permissions.PermissionListVO;
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
@RequestMapping("/system/permission")
@CrossOrigin(origins = {"http://localhost:8080"}, allowCredentials = "true")
public class PermissionController {

    @Autowired
    IPermissionService iPermissionService;



    /**
     * 查询权限\列表
     * @return
     */
    @GetMapping("/search")
    public JsonVO<Object> permissionList(String permissionName) {
        List<PermissionListVO> permissionList= iPermissionService.permissionList(permissionName);
        if(permissionList==null){
            return JsonVO.fail("Database lacks permission information");
        }else {
            return JsonVO.success(permissionList);
        }
    }

    /**
     * 权限 注册
     * @param registerDTO
     * @param userId
     * @return
     */
    @PostMapping("/register")
    public JsonVO<Boolean>  RegisterPermission(@RequestBody PermissionRegisterDTO registerDTO,@RequestHeader("userId") String userId){
        if(iPermissionService.RegisterPermission(registerDTO,userId)){
            return JsonVO.success(true);
        }else {
            return JsonVO.fail(false);
        }
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public JsonVO<Boolean> deletePermission(String id){
        if(iPermissionService.DeletePermission(id)){
            return JsonVO.success(true);
        }else {
            return JsonVO.fail(false);
        }
    }

    /**
     * 更新权限
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public JsonVO<Boolean> updatePermission(@RequestBody PermissionUpdateDTO dto,@RequestHeader("userId") String userId){
        if(iPermissionService.UpdatePermission(dto,userId)){
            return JsonVO.success(true);
        }else {
            return JsonVO.fail(false);
        }
    }

    /**
     * 数据规则注册
     * @param dto
     * @param userId
     * @return
     */
    @PostMapping("/datarule/insert")
    public JsonVO<Boolean> insertDataRule(DataRuleDTO dto, @RequestHeader("userId") String userId){
        if(iPermissionService.DataRuleInsert(dto,userId)){
            return JsonVO.success(true);
        }else {
            return JsonVO.fail(false);
        }
    }

    /**
     * 数据规则编辑
     * @param dto
     * @param userId
     * @return
     */
    @PostMapping("/datarule/update")
    public JsonVO<Boolean> updateDataRule(DataRuleDTO dto, @RequestHeader("userId") String userId){
        if(iPermissionService.DataRuleUpdate(dto,userId)){
            return JsonVO.success(true);
        }else {
            return JsonVO.fail(false);
        }
    }

    /**
     * 数据规则删除
     * @param id
     * @return
     */
    @PostMapping("/datarule/delete")
    public JsonVO<Boolean> deleteDataRule(String id){
        if(iPermissionService.DataRuleDelete(id)){
            return JsonVO.success(true);
        }else {
            return JsonVO.fail(false);
        }
    }
}

