package com.baizhi.controller;


import com.baizhi.entity.Admin;
import com.baizhi.servive.AdminService;
import com.baizhi.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;


    /*验证码*/
    @RequestMapping("/createImg")
    public void createImg(HttpServletResponse response, HttpSession session) throws IOException {
        CreateValidateCode vcode = new CreateValidateCode();
        String code = vcode.getCode(); // 随机验证码
        vcode.write(response.getOutputStream()); // 把图片输出client
        // 把生成的验证码 存入session
        session.setAttribute("ServerCode", code);
    }



    //登陆
    @RequestMapping("login")
    @ResponseBody
    public String login(Admin admin, HttpSession session, String clientCode) {
        /*判断验证码*/
        //获取服务器里的验证码
        String s;
        Object code = session.getAttribute("ServerCode");
        String ServerCode = (String) code;
        if (!clientCode.equals(ServerCode)) {
            s = "验证码不正确";
            return s;
        }
        String ss = adminService.selectOne(admin);
        return ss;
    }
    //安全退出
    @RequestMapping("loginOut")
    public String loginOut(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "redirect:/back/login.jsp";
    }

}
