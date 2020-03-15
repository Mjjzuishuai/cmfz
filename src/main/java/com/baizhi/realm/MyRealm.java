package com.baizhi.realm;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Resources;
import com.baizhi.entity.Role;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    AdminDao adminDao;

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //Md5Hash md5Hash = new Md5Hash("admin","abcd",1024);
        //System.out.println("md5Hash = " + md5Hash);
        //获取用户名
        String username = (String) authenticationToken.getPrincipal();
        AuthenticationInfo authenticationInfo = null;
        //查询数据库
        Admin admin = new Admin();
        admin.setUsername(username);
        Admin adminByDb = adminDao.selectOne(admin);
        if (adminByDb != null) {
            // System.out.println("adminByDb = " + adminByDb);
            //封装AuthenticationInfo返回
            authenticationInfo = new SimpleAccount(adminByDb.getUsername(), adminByDb.getPassword(), ByteSource.Util.bytes(adminByDb.getSalt()), this.getName());

        }
        return authenticationInfo;
    }


    //获取授权信息的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取主身份信息
        String principal = (String) principalCollection.getPrimaryPrincipal();
        //从数据库中查询用户的信息、角色信息、资源的信息.并把角色信息和资源信息封装成list集合返回给shiro
        Admin admin = adminDao.selectAdminInfo(principal);
        List<String> roleList = new ArrayList<String>();
        List<String> resourcesList = new ArrayList<String>();
        List<Role> roles = admin.getRoles();
        for (Role role : roles) {
            roleList.add(role.getRoleName());
            List<Resources> resources = role.getResources();
            for (Resources resource : resources) {
                resourcesList.add(resource.getResourcesName());
            }
        }
        simpleAuthorizationInfo.addRoles(roleList);
        simpleAuthorizationInfo.addStringPermissions(resourcesList);
        return simpleAuthorizationInfo;
    }
}
