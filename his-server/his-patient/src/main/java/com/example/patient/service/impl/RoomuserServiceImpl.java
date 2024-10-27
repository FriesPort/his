package com.example.patient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.patient.entity.Bed;
import com.example.patient.entity.Roomuser;
import com.example.patient.mapper.BedMapper;
import com.example.patient.mapper.RoomuserMapper;
import com.example.patient.service.IBedService;
import com.example.patient.service.IRoomuserService;
import org.springframework.stereotype.Service;

@Service
public class RoomuserServiceImpl extends ServiceImpl<RoomuserMapper, Roomuser> implements IRoomuserService {
}
