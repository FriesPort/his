package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pojo.bed;
import com.example.mapper.BedMapper;
import com.example.service.IBedService;
import org.springframework.stereotype.Service;

@Service
public class IBedImpl extends ServiceImpl<BedMapper, bed> implements IBedService {

}
