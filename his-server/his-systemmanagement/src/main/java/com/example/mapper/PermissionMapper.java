package com.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> SearchListById(String id);

}
