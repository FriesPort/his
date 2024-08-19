package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Hospitals;
import com.example.mapper.HospitalsMapper;
import com.example.service.IHospitalsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author diandian
 * @since 2024-04-07
 */
@Service
public class HospitalsServiceImpl extends ServiceImpl<HospitalsMapper, Hospitals> implements IHospitalsService {

}
