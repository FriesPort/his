package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.RoomMapper;
import com.example.pojo.room;
import com.example.service.IRoomService;
import org.springframework.stereotype.Service;

@Service
public class IRoomImpl extends ServiceImpl<RoomMapper, room> implements IRoomService {
}
