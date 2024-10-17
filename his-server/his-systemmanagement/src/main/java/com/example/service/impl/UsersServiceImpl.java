package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.systemmanagement.users.UserDisplayDTO;
import com.example.dto.systemmanagement.users.UsersAddDTO;
import com.example.dto.systemmanagement.users.UsersDeleteDTO;
import com.example.dto.systemmanagement.users.UsersUpdateDTO;
import com.example.entity.Campus;
import com.example.mapper.CampusMapper;
import com.example.mapper.UsersMapper;
import com.example.service.IUsersService;
import com.example.utils.IdGenerate;
import com.example.vo.systemmanagement.users.UsersAddVO;
import com.example.vo.systemmanagement.users.UsersDeleteVO;
import com.example.vo.systemmanagement.users.UsersDisplayVO;
import com.example.vo.systemmanagement.users.UsersUpdateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    CampusMapper campusMapper;
    @Autowired
    IdGenerate idGenerate;
    Users users=new Users();
    @Override
    public UsersAddVO insertUser(UsersAddDTO usersAddDTO) {

        try{
            BeanUtils.copyProperties(usersAddDTO,users);
        }catch (Exception e){
            e.printStackTrace();
        }
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        users.setUserId(idGenerate.nextUUID(usersAddDTO));
        users.setPassword(passwordEncoder.encode(usersAddDTO.getPassword()));
        users.setCreateTime(LocalDateTime.now());
        users.setEmployeeNumber(idGenerate.nextEmployeeNumber());
        usersMapper.insert(users);
        Users result=usersMapper.selectOne(new QueryWrapper<Users>().eq("user_id",users.getUserId()));
        if(result!=null){
            UsersAddVO usersAddVO=new UsersAddVO();
            List<Users> results=new ArrayList<>();
            results.add(result);
            usersAddVO.setNewUser(results);
            return usersAddVO;
        }
        return null;
    }

    @Override
    public List<UsersDisplayVO> userlist(UserDisplayDTO userDisplayDTO)throws NullPointerException {
        List<Users> userlist=new ArrayList<>();
        List<UsersDisplayVO> displayVOList=new ArrayList<>();
        QueryWrapper queryWrapper=new QueryWrapper();

        if(!userDisplayDTO.getCampusId().equals("all")){
            queryWrapper.eq("campus_id",userDisplayDTO.getCampusId());
        }
        if(!userDisplayDTO.getName().equals("all")){
            queryWrapper.eq("name",userDisplayDTO.getName());
        }
        if(!userDisplayDTO.getEmployeeNumber().equals("all")){
            queryWrapper.eq("employee_number",userDisplayDTO.getEmployeeNumber());
        }

        if(userDisplayDTO.getEmployeeNumber().equals("all")&&userDisplayDTO.getName().equals("all")&&userDisplayDTO.getCampusId().equals("all")){
            userlist=usersMapper.selectList(null);

        }else{
            userlist=usersMapper.selectList(queryWrapper);
        }
        for (Users user : userlist) {
            UsersDisplayVO displayVO = new UsersDisplayVO();
            BeanUtils.copyProperties(user, displayVO);
            Campus campus=campusMapper.selectOne(new QueryWrapper<Campus>().eq("campus_id",user.getCampusId()));
            displayVO.setIsEnable(user.getEnable());
            displayVO.setCampusName(campus.getCampusName());
            displayVOList.add(displayVO);
        }

        return displayVOList;
    }

    @Override
    public boolean UpdateUser(UsersUpdateDTO usersUpdateDTO) {
        Set keyset=usersUpdateDTO.getKey_data().keySet();
        Iterator it=keyset.iterator();
        while (it.hasNext()){
            Object key=it.next();
            Object value=usersUpdateDTO.getKey_data().get(key);
            usersMapper.update(new UpdateWrapper<Users>()
                    .set(String.valueOf(key),String.valueOf(value))
                    .eq("user_id",usersUpdateDTO.getUser_id()));
            Users user=usersMapper.selectOne(new QueryWrapper<Users>()
                    .eq("user_id",usersUpdateDTO.getUser_id())
                    .eq(String.valueOf(key),String.valueOf(value)));
            if (user==null){
                return UsersUpdateVO.fail;
            }
        }

        return UsersUpdateVO.success;
    }

    @Override
    public boolean DeleteUser(UsersDeleteDTO usersDeleteDTO) {
        usersMapper.delete(new QueryWrapper<Users>().eq("user_id",usersDeleteDTO.getUser_id()));
        if(usersMapper.selectOne(new QueryWrapper<Users>()
                .eq("user_id",usersDeleteDTO.getUser_id()))==null){
            return UsersDeleteVO.success;
        }

        return UsersDeleteVO.fail;
    }
}
