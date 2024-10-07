package com.example.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.systemmanagement.user.UserDisplayDTO;
import com.example.dto.systemmanagement.user.UserAddDTO;
import com.example.dto.systemmanagement.user.UserDeleteDTO;
import com.example.dto.systemmanagement.user.UserUpdateDTO;
import com.example.entity.User;
import com.example.mapper.CampusMapper;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import com.example.utils.IdGenerate;
import com.example.vo.systemmanagement.user.UserDeleteVO;
import com.example.vo.systemmanagement.user.UserUpdateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    //添加用户的逻辑不需要返回新创建的用户信息
    @Override
    public Boolean insertUser(UserAddDTO userAddDTO) {
        User user =new User();
        try{
            BeanUtils.copyProperties(userAddDTO, user);
        }catch (Exception e){
            e.printStackTrace();
        }
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        user.setId(idGenerate.nextUUID(userAddDTO));
        user.setPassword(passwordEncoder.encode(userAddDTO.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setEmployeeNumber(idGenerate.nextEmployeeNumber());
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

    @Override
    public IPage<UserDisplayDTO> userlist(UserDisplayDTO userDisplayDTO, Page page){
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

    @Override
    public boolean updateUser(UserUpdateDTO userUpdateDTO) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO,user);
        int rows = userMapper.updateById(user);
        if(rows>=1){
            return UserUpdateVO.success;
        }
        return UserUpdateVO.fail;
    }

    @Override
    public boolean deleteUser(UserDeleteDTO userDeleteDTO) {
        int rows = userMapper.delete(new LambdaQueryWrapper<User>().eq(User::getId, userDeleteDTO.getUser_id()));
//        if(usersMapper.selectOne(new LambdaQueryWrapper<Users>()
//                .eq(Users::getUserId,usersDeleteDTO.getUser_id()))==null){
//            return UsersDeleteVO.success;
//        }
        if(rows>=1){
            return UserDeleteVO.success;
        }
        return UserDeleteVO.fail;
    }
}
