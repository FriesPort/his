package com.example.service.impl;


import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.systemmanagement.user.UserDisplayDTO;
import com.example.dto.systemmanagement.user.UserAddDTO;
import com.example.dto.systemmanagement.user.UserUpdateDTO;
import com.example.dto.systemmanagement.userrole.UserCreateDTO;
import com.example.entity.User;
import com.example.entity.UserRole;
import com.example.mapper.UserMapper;
import com.example.mapper.UserRoleMapper;
import com.example.service.IUserService;
import com.example.utils.IdGenerate;
import com.example.vo.systemmanagement.user.UserDeleteVO;
import com.example.vo.systemmanagement.user.UserDisplayVO;
import com.example.vo.systemmanagement.user.UserUpdateVO;
import com.example.vo.systemmanagement.userrole.UserRoleDisplayVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private IdGenerate idGenerate;

    @Autowired
    private UserRoleMapper userRoleMapper;

    //添加用户的逻辑不需要返回新创建的用户信息
    @Override
    public Boolean insertUser(UserAddDTO userAddDTO,String userId) {
        User user =new User();
        try{
            BeanUtils.copyProperties(userAddDTO, user);
        }catch (Exception e){
            e.printStackTrace();
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User queryUser = userMapper.selectOne(queryWrapper);
        if(queryUser!=null){
            return false;
        }
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        user.setId(idGenerate.nextUUID(userAddDTO));
        user.setPassword(passwordEncoder.encode(userAddDTO.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setEmployeeNumber(idGenerate.nextEmployeeNumber());
        user.setCreateBy(userId);
        int rows = userMapper.insert(user);
//        Users result=usersMapper.selectOne(new QueryWrapper<Users>().eq("user_id",users.getUserId()));
//        if(result!=null){
//            //todo
//            UsersAddVO usersAddVO=new UsersAddVO();
//            List<Users> results=new ArrayList<>();
//            results.add(result);
//            usersAddVO.setNewUser(results);
//            return usersAddVO;
//        }
//        return null;
        if(rows>=1){
            return true;
        }
        return false;
    }

    //这里展示用户表列表会展示出登录用户自己吗？
    @Override
    public IPage<UserDisplayVO> userlist(UserDisplayDTO userDisplayDTO, Page page){
//        List<User> userlist=new ArrayList<>();
//        List<UsersDisplayVO> displayVOList=new ArrayList<>();
//        QueryWrapper queryWrapper=new QueryWrapper();
//
//        if(!userDisplayDTO.getCampusId().equals("all")){
//            queryWrapper.eq("campus_id",userDisplayDTO.getCampusId());
//        }
//        if(!userDisplayDTO.getName().equals("all")){
//            queryWrapper.eq("name",userDisplayDTO.getName());
//        }
//        if(!userDisplayDTO.getEmployeeNumber().equals("all")){
//            queryWrapper.eq("employee_number",userDisplayDTO.getEmployeeNumber());
//        }
//
//        if(userDisplayDTO.getEmployeeNumber().equals("all")&&userDisplayDTO.getName().equals("all")&&userDisplayDTO.getCampusId().equals("all")){
//            userlist= userMapper.selectList(null);
//
//        }else{
//            userlist= userMapper.selectList(queryWrapper);
//        }
//        for (User user : userlist) {
//            UsersDisplayVO displayVO = new UsersDisplayVO();
//            BeanUtils.copyProperties(user, displayVO);
//            Campus campus=campusMapper.selectOne(new QueryWrapper<Campus>().eq("campus_id",user.getCampusId()));
//            displayVO.setIsEnable(user.getEnable());
//            displayVO.setCampusName(campus.getCampusName());
//            displayVOList.add(displayVO);
//        }
//
//        return displayVOList;

        return  userMapper.userList(userDisplayDTO,page);
    }
    //建议值传入修改的字段，用Map接收，然后拼接sql，不需要每次都传入全部字段
    @Override
    public boolean updateUser(UserUpdateDTO userUpdateDTO,String userId) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO,user);
        user.setUpdateBy(userId);
        user.setUpdateTime(LocalDateTime.now());
        int rows = userMapper.updateById(user);
        if(rows>=1){
            return UserUpdateVO.success;
        }
        return UserUpdateVO.fail;
    }

    @Override
    public boolean deleteUser(String id) {
        int rows = userMapper.delete(new LambdaQueryWrapper<User>().eq(User::getId, id));
//        if(usersMapper.selectOne(new LambdaQueryWrapper<Users>()
//                .eq(Users::getUserId,usersDeleteDTO.getUser_id()))==null){
//            return UsersDeleteVO.success;
//        }
        if(rows>=1){
            return UserDeleteVO.success;
        }
        return UserDeleteVO.fail;
    }

    @Override
    public boolean allocateRole(UserCreateDTO userCreateDTO,String userId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userCreateDTO.getUserId());
        userRole.setRoleId(userCreateDTO.getRoleId());
        userRole.setCreateBy(userId);
        userRole.setId(idGenerate.nextUUID(userRole));
        userRole.setCreateTime(LocalDateTime.now());
        int rows = userRoleMapper.insert(userRole);
        if(rows>=1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean userRoleDelete(String userId, String roleId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,userId);
        queryWrapper.eq(UserRole::getRoleId,roleId);
        int rows = userRoleMapper.delete(queryWrapper);
        if(rows>=1){
            return true;
        }
        return false;
    }

    @Override
    public List<UserRoleDisplayVo> userRoleDisplay(String userId) {
        return userMapper.userRoleDisplay(userId);
    }

    @Override
    public List<UserRoleDisplayVo> unownedUserRoleDisplay(String userId) {
        return userMapper.unownedUserRoleDisplay(userId);
    }
}
