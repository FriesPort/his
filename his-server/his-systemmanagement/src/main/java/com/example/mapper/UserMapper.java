package com.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.systemmanagement.user.UserDisplayDTO;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    IPage<UserDisplayDTO> userList(UserDisplayDTO userDisplayDTO, Page page);
}
