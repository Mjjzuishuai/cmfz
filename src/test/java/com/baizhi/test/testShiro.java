package com.baizhi.test;

import com.baizhi.Cmfz;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cmfz.class)
public class testShiro {
    @Test
    public void shiro(){
        //初始化安全管理器工厂
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //从安全管理器中拿安全管理器对象
        SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
        //将安全管理器对象交给shiro管理
        SecurityUtils.setSecurityManager(securityManager);
        //根据shiro中的安全管理器获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //获取令牌  包括身份信息和凭证信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("admin","admin");
        //认证
        try {
            subject.login(usernamePasswordToken);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //查看认证状态
        boolean authenticated = subject.isAuthenticated();
        System.out.println("authenticated = " + authenticated);
    }
}
