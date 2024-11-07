package com.example.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.example.dto.systemmanagement.dict.HisDictItemAddDTO;
import com.example.dto.systemmanagement.dict.HisDictItemEditDTO;
import com.example.entity.HisDict;
import com.example.entity.HisDictItem;
import com.example.mapper.HisDictItemMapper;
import com.example.service.IHisDictItemService;
import com.example.utils.IdGenerate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 数据字典子项表 服务实现类
 * </p>
 *
 * @author diandian
 * @since 2024-11-07
 */
@Slf4j
@Service
public class HisDictItemServiceImpl extends ServiceImpl<HisDictItemMapper, HisDictItem> implements IHisDictItemService {

    @Autowired
    IdGenerate idGenerate;

    @Override
    public Boolean addDictItem(HisDictItemAddDTO dto,String userId) {
        if(!Db.lambdaQuery(HisDict.class).eq(HisDict::getId,dto.getDictId()).exists()){
            log.info("主表记录不存在");
            return false;
        }
        HisDictItem item = new HisDictItem();
        BeanUtils.copyProperties(dto, item);
        item.setId(idGenerate.nextUUID(dto));
        item.setCreateBy(userId);
        item.setCreateTime(LocalDateTime.now());
        int i=baseMapper.insert(item);
        return i>0;
    }

    @Override
    public Boolean editDictItem(HisDictItemEditDTO dto, String userId) {
        HisDictItem item = new HisDictItem();
        LambdaUpdateWrapper<HisDictItem> sql=new LambdaUpdateWrapper<>();
        sql.eq(HisDictItem::getId,dto.getId());
        if(StrUtil.isNotEmpty(dto.getItemText())){
            sql.set(HisDictItem::getItemText,dto.getItemText());
        }else if(StrUtil.isNotEmpty(dto.getItemValue())){
            sql.set(HisDictItem::getItemValue,dto.getItemValue());
        }else if(StrUtil.isNotEmpty(dto.getDescription())){
            sql.set(HisDictItem::getDescription,dto.getDescription());
        }
        item.setUpdateBy(userId);
        item.setUpdateTime(LocalDateTime.now());
        int i=baseMapper.update(sql);
        return i>0;
    }
}
