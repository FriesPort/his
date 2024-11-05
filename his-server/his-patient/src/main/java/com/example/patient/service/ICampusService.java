package com.example.patient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.patient.entity.Campus;
import com.example.vo.patient.CampusVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xw
 * @since 2024-05-09
 */
public interface ICampusService extends IService<Campus> {
    List<CampusVO> all_dep_search();
}
