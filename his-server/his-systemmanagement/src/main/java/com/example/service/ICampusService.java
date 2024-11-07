package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.systemmanagement.campus.CampusUpdateDTO;
import com.example.entity.Campus;
import org.apache.ibatis.ognl.BooleanExpression;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author diandian
 * @since 2024-11-05
 */
public interface ICampusService extends IService<Campus> {

    Boolean add(Campus campus,String userId);

    Boolean update(CampusUpdateDTO dto,String userId);

}
