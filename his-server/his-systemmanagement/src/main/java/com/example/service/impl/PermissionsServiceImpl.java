package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Permissions;
import com.example.mapper.PermissionsMapper;
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
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permissions> implements IPermissionsService {

    @Autowired
    PermissionsMapper permissionsMapper;
    @Override
    public List<PermissionListVO> permissionList() {
        List<Permissions> permissionsList=permissionsMapper.selectList(null);
        if(permissionsList==null){
            return null;
        }
        List<PermissionListVO> permissionListVOList=new ArrayList<>();
        for(Permissions permission:permissionsList){
            PermissionListVO permissionListVO=new PermissionListVO();
            BeanUtils.copyProperties(permission,permissionListVO);
            permissionListVOList.add(permissionListVO);
        }
        return permissionListVOList;
    }
}
