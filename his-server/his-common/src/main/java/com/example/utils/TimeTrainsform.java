package com.example.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeTrainsform {
    /**
     * 将日期字符串转换为当天的开始时间 LocalDateTime（00:00:00）
     */
     public LocalDateTime getStartOfDay(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date.atStartOfDay();
    }

    /**
     * 将日期字符串转换为当天的结束时间 LocalDateTime（23:59:59）
     */
    public LocalDateTime getEndOfDay(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date.atTime(23, 59, 59);
    }

}
