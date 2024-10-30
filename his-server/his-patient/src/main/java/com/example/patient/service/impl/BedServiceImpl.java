package com.example.patient.service.impl;

import com.example.patient.entity.Bed;
import com.example.patient.mapper.BedMapper;
import com.example.patient.service.IBedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xw
 * @since 2024-05-09
 */
@Service
public class BedServiceImpl extends ServiceImpl<BedMapper, Bed> implements IBedService {
}
