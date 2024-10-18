package com.example.controller;


import com.example.service.IRolePermissionService;
import com.example.service.IRoleService;
import com.example.vo.JsonVO;
import com.example.vo.systemmanagement.roles.RoleCreateVO;
import com.example.vo.systemmanagement.roles.RoleListVO;
import com.example.dto.systemmanagement.roles.RoleCreateDTO;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    IRoleService iRoleService;

    @Autowired
    IRolePermissionService iRolePermissionService;


    @PostMapping("/register")
    @ApiOperation(value = "自定义角色")
    public JsonVO<RoleCreateVO> RoleCreate(@RequestBody RoleCreateDTO roleCreateDTO) {
        return JsonVO.success(iRolePermissionService.definedRole(roleCreateDTO));
    }

    @GetMapping("/msg/display")
    public JsonVO<List<RoleListVO>> RoleList() {

        return JsonVO.success(iRoleService.rolelist());
    }

    @GetMapping("/permissiondisplay")
    public JsonVO<List<String>> PermissionDisplay(@RequestParam String roleId) {
        return null;
    }
}

