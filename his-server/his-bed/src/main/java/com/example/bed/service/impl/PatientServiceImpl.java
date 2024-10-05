package com.example.bed.service.impl;

import com.example.bed.entity.Patient;
import com.example.bed.mapper.PatientInformationMapper;
import com.example.bed.service.IPatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xw
 * @since 2024-04-27
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientInformationMapper, Patient> implements IPatientService {

}
