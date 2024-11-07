package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.systemmanagement.dict.HisDictItemAddDTO;
import com.example.dto.systemmanagement.dict.HisDictItemEditDTO;
import com.example.entity.HisDictItem;

/**
 * <p>
 * 数据字典子项表 服务类
 * </p>
 *
 * @author diandian
 * @since 2024-11-07
 */
public interface IHisDictItemService extends IService<HisDictItem> {

    Boolean addDictItem(HisDictItemAddDTO dto,String userId);

    Boolean editDictItem(HisDictItemEditDTO dto,String userId);

}
