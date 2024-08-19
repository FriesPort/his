package com.example.utils;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
public class IdGenerate implements IdentifierGenerator {


    @Override
    public String nextUUID(Object entity) {
        // 可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
        String bizKey = entity.getClass().getName();
        log.info("bizKey:{}", bizKey);
        MetaObject metaObject = SystemMetaObject.forObject(entity);
        // 使用UUID生成唯一ID
        String uuid = UUID.randomUUID().toString();
        log.info("为{}生成主键值->:{}", bizKey, uuid);
        return uuid;
    }

    @Override
    public Number nextId(Object entity) {
        return null;
    }

    public String nextEmployeeNumber(){
        // 生成随机数
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000; // 生成 6 位随机数

        // 拼接前缀
        String employeeNumber = "E" + randomNumber;

        return employeeNumber;
    }


}
