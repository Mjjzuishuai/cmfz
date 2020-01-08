package com.baizhi.aspect;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//表示在目标方法执行过程中执行通知方法
@Retention(RetentionPolicy.RUNTIME)
//表示在方法上使用
@Target(ElementType.METHOD)
public @interface LogAnnotation {
    String value();
}
