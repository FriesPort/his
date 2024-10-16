package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAspectJAutoProxy
@SpringBootApplication
@MapperScan("com.example.patient.mapper")
@ComponentScan(basePackages = {"com.example.patient", "com.example.aop","com.example.utils"})  // 扫描多个组件包
public class HisPatientApplication {

    public static void main(String[] args) {
        SpringApplication.run(HisPatientApplication.class, args);
    }

}
