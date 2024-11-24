package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.HisDict;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author diandian
 * @since 2024-11-07
 */
public interface IHisDictService extends IService<HisDict> {
    Boolean editDict(HisDict hisDict,String userId);
}
