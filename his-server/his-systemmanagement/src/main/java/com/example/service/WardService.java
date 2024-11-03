package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Ward;
import com.example.vo.systemmanagement.ward.WardVo;

import java.util.List;

/**
* @author 18255
* @description 针对表【ward】的数据库操作Service
* @createDate 2024-10-31 16:49:07
*/
public interface WardService extends IService<Ward> {

    List<WardVo> listWardVo();
}
