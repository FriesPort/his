package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.systemmanagement.hospital.HospitalUpdateDTO;
import com.example.entity.Hospital;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author diandian
 * @since 2024-11-06
 */
public interface IHospitalService extends IService<Hospital> {

    Boolean add(Hospital hospital,String userId);

    Boolean update(HospitalUpdateDTO hospital, String userId);

}
