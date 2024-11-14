package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.HisDict;
import com.example.mapper.HisDictMapper;
import com.example.service.IHisDictService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author diandian
 * @since 2024-11-07
 */
@Service
public class HisDictServiceImpl extends ServiceImpl<HisDictMapper, HisDict> implements IHisDictService {

    @Override
    public Boolean editDict(HisDict hisDict, String userId) {
        return null;
    }
}
