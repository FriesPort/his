package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Ward;
import com.example.service.WardService;
import com.example.mapper.WardMapper;
import org.springframework.stereotype.Service;

/**
* @author 18255
* @description 针对表【ward】的数据库操作Service实现
* @createDate 2024-10-31 16:49:07
*/
@Service
public class WardServiceImpl extends ServiceImpl<WardMapper, Ward>
    implements WardService{

}




