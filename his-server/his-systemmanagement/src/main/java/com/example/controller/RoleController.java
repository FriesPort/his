package com.example.controller;


import com.example.dto.systemmanagement.rolepermission.RoleAllocationDTO;
import com.example.service.IRolePermissionService;
import com.example.service.IRoleService;
import com.example.vo.JsonVO;
import com.example.vo.systemmanagement.role.PermissionDisplayVO;
import com.example.vo.systemmanagement.role.RoleCreateVO;
import com.example.vo.systemmanagement.role.RoleListVO;
import com.example.dto.systemmanagement.roles.RoleCreateDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    IRoleService iRoleService;

    @Autowired
    IRolePermissionService iRolePermissionService;

    /**
     * 删除角色
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public JsonVO<Boolean> deleteRole(String id){
        if(iRoleService.DeleteRole(id)){
            return JsonVO.success(true);
        }else {
            return JsonVO.fail(false);
        }
    }

    /**
     * 自定义角色
     * @param roleCreateDTO
     * @return RoleCreateVO
     */
    @PostMapping("/register")
    @ApiOperation(value = "自定义角色")
    public JsonVO<RoleCreateVO> RoleCreate(@RequestBody RoleCreateDTO roleCreateDTO, @RequestHeader("userId") String userId) {
        return JsonVO.success(iRolePermissionService.definedRole(roleCreateDTO, userId));
    }

    /**
     * 角色查询
     * @return List<RoleListVO>
     */
    @GetMapping("/search")
    public JsonVO<List<RoleListVO>> RoleList(String role_name) {

        return JsonVO.success(iRoleService.rolelist(role_name));
    }

    /**
     * 获取角色所拥有的权限
     * @param roleId
     * @return permissiondisplay
     */
    @GetMapping("/permissiondisplay")
    public JsonVO<List<PermissionDisplayVO>> PermissionDisplay(@RequestParam String roleId) {
        return JsonVO.success(iRolePermissionService.permissionDisplay(roleId));
    }

    /**
     * 角色分配权限
     * @param allocationDTO
     * @param userId
     * @return
     */
    @PostMapping("/allocation")
    public JsonVO<Boolean> RoleAllocation(@RequestBody RoleAllocationDTO allocationDTO,
                                          @RequestHeader("userId") String userId) {
        log.info("userId:{}",userId);
        if(iRolePermissionService.roleAllocation(allocationDTO,userId)){
            return JsonVO.success(true);
        }else{
            return JsonVO.fail(false);
        }
    }

}

