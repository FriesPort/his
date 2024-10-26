package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class HisDomainApplicationTests {

    @Test
    void contextLoads() {
        int i=lengthOfLongestSubstring("pwwkew");
        System.out.println(i);
    }

    public int lengthOfLongestSubstring(String s) {
        List<Character> charList=new ArrayList<>();
        for(char c:s.toCharArray()){
            if(!charList.contains(c)){
                charList.add(c);
            }
        }
        return charList.size();
    }

}
