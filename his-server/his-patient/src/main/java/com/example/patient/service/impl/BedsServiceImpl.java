package com.example.patient.service.impl;

import com.example.patient.entity.Beds;
import com.example.patient.mapper.BedsMapper;
import com.example.patient.service.IBedsService;
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
public class BedsServiceImpl extends ServiceImpl<BedsMapper, Beds> implements IBedsService {
}
