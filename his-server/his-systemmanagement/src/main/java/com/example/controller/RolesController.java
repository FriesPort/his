package com.example.controller;


import com.example.dto.systemmanagement.roles.RoleCreateDTO;
import com.example.service.IRolePermissionsService;
import com.example.service.IRolesService;
import com.example.vo.JsonVO;
import com.example.vo.systemmanagement.roles.RoleCreateVO;
import com.example.vo.systemmanagement.roles.RoleListVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/roles")
public class RolesController{

    @Autowired
    IRolesService iRolesService;

    @Autowired
    IRolePermissionsService iRolePermissionsService;


    @PostMapping("/permission/allocation")
    @ApiOperation(value = "自定义角色")
    public JsonVO<RoleCreateVO> RoleCreate(@RequestBody RoleCreateDTO roleCreateDTO) {
        return JsonVO.success(iRolePermissionsService.definedRole(roleCreateDTO));
    }

    @GetMapping("/msg/display")
    public JsonVO<List<RoleListVO>> RoleList() {

        return JsonVO.success(iRolesService.rolelist());
    }
}

