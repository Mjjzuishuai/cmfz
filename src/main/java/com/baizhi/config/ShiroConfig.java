package com.baizhi.config;

import com.baizhi.realm.MyRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

//声明配置类 交由工厂管理
@Configuration
public class ShiroConfig {


    //创建shiro过滤器工厂,并交由工厂管理
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(){
        //创建shiro过滤器工厂对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //往工厂里添加管理器对象
        shiroFilterFactoryBean.setSecurityManager(getSecurityManager());
        //配置过滤器链，就是设置哪些资源需要认证才能访问，哪些资源不需要认证就可以访问
        HashMap<String,String> map = new HashMap<>();
        map.put("/**","authc");
        map.put("/boot/**","anon");
        map.put("/echarts/**","anon");
        map.put("/jqgrid/**","anon");
        map.put("/json/**","anon");
        map.put("/admin/createImg","anon");
        map.put("/kindeditor/**","anon");
        // 返回状态码 302 表示该资源为认证资源 shiro拦截
        map.put("/admin/login","anon");
        // 配置登陆页面
        shiroFilterFactoryBean.setLoginUrl("/back/login.jsp");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    };


    //创建安全管理器对象,并交由工厂管理
    @Bean
    public SecurityManager getSecurityManager(){
        //创建安全管理器的实现类对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //创建shiro缓存，目的是如果用户登陆了，每次刷新页面不用每次都去数据库中查询该用户的权限
        EhCacheManager ehCacheManager = new EhCacheManager();
        defaultWebSecurityManager.setCacheManager(ehCacheManager);
        //往安全管理器中添加自定义数据源对象
        defaultWebSecurityManager.setRealm(getMyrealm());
        return defaultWebSecurityManager;
    }


    //创建数据源对象,并交由工厂管理
    @Bean
    public MyRealm getMyrealm(){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(getCredentialsMatcher());
        return myRealm;
    }


    //创建Md5凭证匹配器,并交由工厂管理
    @Bean
    public CredentialsMatcher getCredentialsMatcher(){
        //创建Md5凭证匹配器对象
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
}
