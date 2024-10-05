package com.example;

import com.example.utils.IdGenerate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HisCommonApplicationTests {

    @Autowired
    IdGenerate idGenerate;
    @Test
    void contextLoads() {
        System.out.println(idGenerate.nextUUID("username"));
    }

}
