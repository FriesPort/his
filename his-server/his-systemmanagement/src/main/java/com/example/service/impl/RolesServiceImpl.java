package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Roles;
import com.example.mapper.RolesMapper;
import com.example.service.IRolesService;
import com.example.vo.systemmanagement.roles.RoleListVO;
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
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements IRolesService {


    @Autowired
    RolesMapper rolesMapper;
    @Override
    public List<RoleListVO> rolelist() {
        List<Roles> roles=rolesMapper.selectList(null);
        List<RoleListVO> result=new ArrayList<>();
        for(Roles role:roles){
            RoleListVO vo=new RoleListVO();
            BeanUtils.copyProperties(role,vo);
            result.add(vo);
        }
        return result;
    }
}
