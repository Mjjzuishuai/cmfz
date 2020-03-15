package com.baizhi.aspect;


import com.alibaba.fastjson.JSON;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Mylog;
import com.baizhi.entity.UserLocation;
import com.baizhi.servive.MylogService;
import com.baizhi.servive.UserService;
import com.baizhi.util.MyWebWare;
import io.goeasy.GoEasy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    RedisTemplate redisTemplate;

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


    //通用缓存
    @Around(value = "@annotation(com.baizhi.aspect.addCache)")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint){
        //思路:
        //依然使用redis数据库hash存储方式存储缓存,使用自定义注解的方式切入,并拿类对象作为存储的Key,方法名和存数作为hash的key
        //当走查询的时候经过切面类，判断redis中有没有数据，有则返回，没有则执行后续方法，并放到redis中。

        //代码:
        //通过工具类拿reidsTemplate对象
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
        //拿目标类类对象作为 redis存储过程中的key
          String clazz = proceedingJoinPoint.getTarget().getClass().toString();
        System.out.println("clazz = " + clazz);
        String name = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        String key = name;
        for (int i = 0; i < args.length; i++) {
            key += args[i];
        }
        //判断reids中有没有数据（也就是有没有缓存）
        Object o = redisTemplate.opsForHash().get(clazz, key);
        //如果redis中有数据,则返回查询结果
        if(o!=null){
            return o;
        }
        //如果没有数据,则执行后续方法,并存入数据库
        try {
            Object proceed = proceedingJoinPoint.proceed();
            redisTemplate.opsForHash().put(clazz,key,proceed);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
    //接上：一旦执行增删改方法，立即删除缓存
    @Around(value = "@annotation(com.baizhi.aspect.ClearCache)")
    public Object clearCache(ProceedingJoinPoint proceedingJoinPoint){
        //通过工具类拿reidsTemplate对象
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
        //拿目标类类对象作为 redis存储过程中的key
        String clazz = proceedingJoinPoint.getTarget().getClass().toString();
        redisTemplate.delete(clazz);
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
