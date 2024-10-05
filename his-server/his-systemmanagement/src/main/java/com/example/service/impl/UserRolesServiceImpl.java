package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.systemmanagement.userrole.UserCreateDTO;
import com.example.dto.systemmanagement.userrole.UserRoleDeleteDTO;
import com.example.dto.systemmanagement.userrole.UserRolePermissionSearchDTO;
import com.example.dto.systemmanagement.userrole.UserRoleUpdateDTO;
import com.example.entity.*;
import com.example.mapper.*;
import com.example.service.IUserRolesService;
import com.example.utils.IdGenerate;
import com.example.vo.systemmanagement.userrole.UserCreateVO;
import com.example.vo.systemmanagement.userrole.UserRolePermissionSearchVO;
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
 * @since 2024-04-17
 */
@Service
public class UserRolesServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRolesService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RolesMapper rolesMapper;
    @Autowired
    RolePermissionsMapper rolePermissionsMapper;
    @Autowired
    PermissionsMapper permissionsMapper;
    @Autowired
    IdGenerate idGenerate;

    User user =new User();
    Roles roles=new Roles();
    Permissions permissions=new Permissions();
    UserRole userRole =new UserRole();


    @Override
    public UserCreateVO defineUser(UserCreateDTO userCreateDTO) {
        user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("username",userCreateDTO.getUsername()));
        List<String> rolelist=userCreateDTO.getRoles();
        List<UserRole> exitrole= userRoleMapper.selectList(new QueryWrapper<UserRole>()
                .eq("user_id", user.getId()));
        for(String name:rolelist){
            roles=rolesMapper.selectOne(new QueryWrapper<Roles>().eq("role_name",name));
            for(UserRole ex:exitrole){
                if(ex.getRoleId().equals(roles.getRoleId())){
                    return null;
                }
            }
            userRole.setRoleId(roles.getRoleId());
            userRole.setUrId(idGenerate.nextUUID(userCreateDTO));
            userRole.setUserId(user.getId());
            userRoleMapper.insert(userRole);
        }
        UserCreateVO userCreateVO=new UserCreateVO();
        userCreateVO.setUsername(userCreateDTO.getUsername());
        List<UserRole> ur= userRoleMapper.selectList(new QueryWrapper<UserRole>()
                .eq("user_id", user.getId()));
        List<String> rolename=new ArrayList<>();
        for(UserRole userRole1 :ur){
            rolename.add( rolesMapper
                    .selectOne(
                           new QueryWrapper<Roles>().eq("role_id", userRole1.getRoleId())
            ).getRoleName());
        }
        userCreateVO.setRolename(rolename);
        return userCreateVO;
    }

    @Override
    public Boolean userperdelete(UserRoleDeleteDTO userRoleDeleteDTO) {
        boolean condition=true;
        for(int i=0;i<userRoleDeleteDTO.getDelete_role().size();i++){
            Roles role=rolesMapper.selectOne(new QueryWrapper<Roles>()
                    .eq("role_name",userRoleDeleteDTO.getDelete_role().get(i)));
            //执行删除操作
            condition= userRoleMapper.delete(new QueryWrapper<UserRole>()
                    .eq("user_id", userRoleDeleteDTO.getUser_id())
                    .eq("role_id", role.getRoleId()))>0;
            if(condition==false)
                break;
        }


        return condition;

    }

    @Override
    public Boolean userperupdate(List<UserRoleUpdateDTO> userRoleUpdateDTO) {
        boolean condition=true;
        for(int i = 0; i< userRoleUpdateDTO.size(); i++){
            userRoleMapper.update(new UpdateWrapper<UserRole>()
                    .set("role_id", userRoleUpdateDTO.get(i).getUpdate_role())
                    .eq("user_id", userRoleUpdateDTO.get(i).getUser_id())
                    .eq("role_id", userRoleUpdateDTO.get(i).getRole_id()));
            userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>()
                    .eq("role_id", userRoleUpdateDTO.get(i).getRole_id())
                    .eq("user_id", userRoleUpdateDTO.get(i).getUser_id()));
            if(userRole !=null){
                condition=false;
                break;
            }
            if(!condition){
                break;
            }
        }
        return condition;
    }

    @Override
    public List<UserRolePermissionSearchVO> all_search(UserRolePermissionSearchDTO userRolePermissionSearchDTO) {
        String userid=userRolePermissionSearchDTO.getUser_id();
        List<RolePermissions> rolePermissions=new ArrayList<>();
        List<Roles> rolesList=new ArrayList<>();
        List<UserRole> roleid= userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id",userid));
        for(UserRole userRole:roleid){
            Roles role=rolesMapper.selectOne(new QueryWrapper<Roles>().eq("role_id",userRole.getRoleId()));
            if(!rolesList.contains(role)){
                rolesList.add(role);
            }
        }
        List<UserRolePermissionSearchVO> voList=new ArrayList<>();
        //查询角色对应权限
        for(int i=0;i<roleid.size();i++){
            //角色权限对应关系
            rolePermissions=rolePermissionsMapper
                    .selectList(new QueryWrapper<RolePermissions>().eq("role_id",roleid.get(i).getRoleId()));
            UserRolePermissionSearchVO<Permissions> vo=new UserRolePermissionSearchVO();
            vo.setRoleid(rolesList.get(i).getRoleId());
            vo.setRolename(rolesList.get(i).getRoleName());
            List<Permissions> pname=new ArrayList<>();
            for(int j=0;j<rolePermissions.size();j++){
                permissions=permissionsMapper
                        .selectOne(new QueryWrapper<Permissions>()
                                .eq("permission_id",rolePermissions.get(j).getPermissionId()));
                if(!pname.contains(permissions)){
                    pname.add(permissions);
                }
                if(j==rolePermissions.size()-1){
                    vo.setPermission(pname);
                }
            }
                voList.add(vo);
        }
        return voList;
    }
}
