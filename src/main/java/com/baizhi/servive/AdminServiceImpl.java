package com.baizhi.servive;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        String username = admin.getUsername();
        Admin admin1 = adminDao.selectOne(admin);
        String message;
        if(admin1 == null){
            message="用户名或密码错误";
            return message;
        }else {
            HttpSession session = request.getSession();
            session.setAttribute("admin",admin);
            return "success";
        }
    }
}
