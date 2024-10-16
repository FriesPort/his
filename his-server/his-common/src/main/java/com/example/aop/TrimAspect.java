package com.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;

@Aspect
@Component
public class TrimAspect {

    @Before("execution(* com.example.patient.controller..*(..))")  // 拦截 controller类下的所有方法
    public void trimArgs(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();  // 获取方法的所有参数
        for (Object arg : args) {
            if (arg != null) {
                trimStringFields(arg);  // 处理所有传入的参数，去除空格
            }
        }
    }

    private void trimStringFields(Object target) throws IllegalAccessException {
        if (target == null) {
            return;
        }

        // 检查是否是集合类型，并递归处理集合中的对象
        if (target instanceof Collection) {
            for (Object element : (Collection<?>) target) {
                trimStringFields(element);
            }
        } else {
            Field[] fields = target.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType().equals(String.class)) {
                    String value = (String) field.get(target);
                    if (value != null) {
                        field.set(target, value.trim());
                    }
                }
            }
        }
    }
}
