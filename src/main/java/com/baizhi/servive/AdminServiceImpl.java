package com.baizhi.servive;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;
    @Autowired
    HttpServletRequest request;

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public String selectOne(Admin admin) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(admin.getUsername(),admin.getPassword());
        String msg;
        try {
            subject.login(usernamePasswordToken);
        }catch (UnknownAccountException exception){
            msg="用户名不正确";
            return msg;
        }catch (IncorrectCredentialsException exception){
            msg = "密码不正确";
            return msg;
        }
        return "success";


       /* String username = admin.getUsername();
        Admin admin1 = adminDao.selectOne(admin);
        String message;
        if(admin1 == null){
            message="用户名或密码错误";
            return message;
        }else {
            HttpSession session = request.getSession();
            session.setAttribute("admin",admin);
            return "success";
        }*/
    }

    @Override
    public List<Admin> selectAll() {
        List<Admin> admins = adminDao.selectAll();
        return admins;
    }

    @Override
    public void add(Admin admin) {
        adminDao.insert(admin);
    }

    @Override
    public void delete(String id) {
        adminDao.deleteByPrimaryKey(id);
    }

    @Override
    public void upDate(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }
}
