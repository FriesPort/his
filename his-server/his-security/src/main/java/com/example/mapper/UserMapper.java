package com.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author diandian
 * @since 2024-10-06
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<String> SearchPermissionByUserId(String userId);


}
