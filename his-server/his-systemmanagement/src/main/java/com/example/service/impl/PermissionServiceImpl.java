package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Permission;
import com.example.mapper.PermissionMapper;
import com.example.service.IPermissionsService;
import com.example.vo.systemmanagement.permissions.PermissionListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionsService {

    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public List<PermissionListVO> permissionList() {
        List<Permission> permissionsList= permissionMapper.selectList(null);
        if(permissionsList==null){
            return null;
        }
        List<PermissionListVO> permissionListVOList=new ArrayList<>();
        for(Permission permission:permissionsList){
            PermissionListVO permissionListVO=new PermissionListVO();
            BeanUtils.copyProperties(permission,permissionListVO);
            permissionListVOList.add(permissionListVO);
        }
        return permissionListVOList;
    }
}
