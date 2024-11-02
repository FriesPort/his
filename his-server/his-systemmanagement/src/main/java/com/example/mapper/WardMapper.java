package com.example.mapper;

import com.example.entity.Ward;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.systemmanagement.ward.WardVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 18255
* @description 针对表【ward】的数据库操作Mapper
* @createDate 2024-10-31 16:49:07
* @Entity entity.com.example.Ward
*/
@Mapper
public interface WardMapper extends BaseMapper<Ward> {

    List<WardVo> listWardVo();
}




