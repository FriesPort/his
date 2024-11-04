package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Role;
import com.example.entity.RolePermission;
import com.example.mapper.RoleMapper;
import com.example.mapper.RolePermissionMapper;
import com.example.service.IRoleService;
import com.example.vo.systemmanagement.role.RoleListVO;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Override
    public Boolean DeleteRole(String id) {
        int rows = rolePermissionMapper.delete(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId,id));
        int lits = roleMapper.delete(new LambdaQueryWrapper<Role>().eq(Role::getId,id));
        log.info("rows：{}",rows);
        if(rows>0||(rows>0&&lits>0)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<RoleListVO> rolelist(String role_name) {
        List<Role> roles= new ArrayList<>();
        if(StringUtil.isNullOrEmpty(role_name)){
            roles=roleMapper.selectList(null);
        }else{
            roles=roleMapper.selectList(new LambdaQueryWrapper<Role>().like(Role::getName,role_name));
        }

        List<RoleListVO> result=new ArrayList<>();
        for(Role role:roles){
            RoleListVO vo=new RoleListVO();
            BeanUtils.copyProperties(role,vo);
            result.add(vo);
        }
        return result;
    }
}
