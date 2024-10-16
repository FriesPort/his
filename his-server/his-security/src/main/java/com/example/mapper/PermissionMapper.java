package com.example.mapper;



import com.example.entity.PermissionPathMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
@Mapper
public interface PermissionMapper{
    public List<PermissionPathMap> getPermissionPath();
}
