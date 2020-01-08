package com.baizhi.aspect;


import com.alibaba.fastjson.JSON;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Mylog;
import com.baizhi.entity.UserLocation;
import com.baizhi.servive.MylogService;
import com.baizhi.servive.UserService;
import io.goeasy.GoEasy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
public class MyAspect {
    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    MylogService mylogService;

    @Around(value = "@annotation(com.baizhi.aspect.LogAnnotation)")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //什么人
        Object admin = request.getSession().getAttribute("admin");
        Admin admin1 = (Admin) admin;
        String username = admin1.getUsername();
        //什么时间
        Date date = new Date();
        //干了什么
        MethodSignature 方法对象 = (MethodSignature) proceedingJoinPoint.getSignature();
        Method 方法 = 方法对象.getMethod();
        LogAnnotation 注解 = 方法.getAnnotation(LogAnnotation.class);
        String value = 注解.value();
        //结果是什么
        String status = null;
        try {
            Object proceed = proceedingJoinPoint.proceed();
            status = "success";
            return proceed;
        } catch (Throwable throwable) {
            status = "error";
            throw throwable;
        } finally {
            Mylog mylog = new Mylog();
            mylog.setId(UUID.randomUUID().toString());
            mylog.setAdminname(username);
            mylog.setOperatedate(date);
            mylog.setThing(value);
            mylog.setStatus(status);
            mylogService.insert(mylog);
        }
    }

    @After(value = "@annotation(com.baizhi.aspect.GoEasy)")
    public Object after(){
        Integer integer1 = userService.selectByRegisterTime("男", 1);
        Integer integer2 = userService.selectByRegisterTime("女", 1);
        Integer integer3 = userService.selectByRegisterTime("男", 7);
        Integer integer4 = userService.selectByRegisterTime("女", 7);
        Integer integer5 = userService.selectByRegisterTime("男", 30);
        Integer integer6 = userService.selectByRegisterTime("女", 30);
        Integer integer7 = userService.selectByRegisterTime("男", 365);
        Integer integer8 = userService.selectByRegisterTime("女", 365);
        ArrayList<Integer> manList = new ArrayList<>();
        ArrayList<Integer> womanList= new ArrayList<>();
        manList.add(integer1);
        manList.add(integer3);
        manList.add(integer5);
        manList.add(integer7);
        womanList.add(integer2);
        womanList.add(integer4);
        womanList.add(integer6);
        womanList.add(integer8);
        HashMap<String, List> map = new HashMap<>();
        map.put("man",manList);
        map.put("woman",womanList);
        String s = JSON.toJSONString(map);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io","BC-198a2526cea5481f81c9f8fcdffaafb1");
        goEasy.publish("cmfz",s);
        return null;
    }

    @After(value = "@annotation(com.baizhi.aspect.GoEasy)")
    public Object after02(){
        List<UserLocation> manList = userService.selectByLocation("男");
        List<UserLocation> womanList = userService.selectByLocation("女");
        HashMap<String, List<UserLocation>> map = new HashMap<>();
        map.put("man",manList);
        map.put("woman",womanList);
        String s = JSON.toJSONString(map);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io","BC-198a2526cea5481f81c9f8fcdffaafb1");
        goEasy.publish("cmfz",s);
        return null;
    }
}
