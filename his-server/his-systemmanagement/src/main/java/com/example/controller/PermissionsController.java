package com.example.controller;


import com.example.service.IPermissionsService;
import com.example.vo.JsonVO;
import com.example.vo.systemmanagement.permissions.PermissionListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/permissions")
public class PermissionsController {

    @Autowired
    IPermissionsService iPermissionsService;

    @GetMapping("/msg/display")
    public JsonVO<Object> permissionList() {
        List<PermissionListVO> permissionList=iPermissionsService.permissionList();
        if(permissionList==null){
            return JsonVO.fail("Database lacks permission information");
        }else {
            return JsonVO.success(permissionList);
        }
    }
}

