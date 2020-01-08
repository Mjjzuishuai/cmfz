package com.baizhi.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//在没有由工厂管理的类中拿工厂管理的类
@Component
public class MyWebWare implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    //现在上边的借口，会自动把工厂对象传过来。然后我们给工厂对象声明为成员变量
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    //通过工厂对象根据名字拿对象
    public static Object getBeanByName(String name) {
        return applicationContext.getBean(name);
    }
    //通过工厂对象根据类型拿对象
    public static Object getBeanByClass(Class clazz) {
        return applicationContext.getBean(clazz);
    }
}
