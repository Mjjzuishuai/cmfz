package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.entity.UserLocation;
import com.baizhi.entity.UserPageDto;
import com.baizhi.servive.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2020-01-02 22:25:07
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;
    //分页茶所有
    @RequestMapping("selectByPage")
    public UserPageDto selectByPage(Integer page,Integer rows){
        //分页查
        List<User> users = userService.selectByPage(page, rows);
        //查总行数
        Integer totalCount = userService.selectTotalCount();
        //查总页数
        Integer integer = userService.totalTotalPage(rows);
        UserPageDto userPageDto = new UserPageDto();
        userPageDto.setPage(page);
        userPageDto.setTotal(integer);
        userPageDto.setRecords(totalCount);
        userPageDto.setRows(users);
        return userPageDto;
    }

    //增删改
    @RequestMapping("edit")
    public Map edit(User user,String oper,String[] id){
        if(oper.equals("edit")){
            userService.ipDate(user);
        }else if(oper.equals("add")){
            userService.insert(user);
        }else {
            userService.deleteByIds(id);
        }
        return null;
    }


    //查询一天,一周,一个月,一年内注册的用户
    @RequestMapping("selectByRegisterTime")
    public Map selectByRegisterTime(){
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
        return map;
    }
    //查询每个地区男女的注册人数
    @RequestMapping("selectByLocation")
    public Map selectByLocation(){
        List<UserLocation> manList = userService.selectByLocation("男");
        List<UserLocation> womanList = userService.selectByLocation("女");
        HashMap<String, List<UserLocation>> map = new HashMap<>();
        map.put("man",manList);
        map.put("woman",womanList);
        return map;
    }

    //前台：登陆
    @RequestMapping("login")
    public Map login(String phone,String password){
        Map map = userService.selectByName(phone, password);
        return map;
    }

    //前台：发送验证码
    @RequestMapping("sendCode")
    public Map sendCode(String phone){
        Map map = userService.sendCode(phone);
        return map;
    }

    //前台：校验短信验证码
    @RequestMapping("checkCode")
    public Map checkCode(String phone, String clientCode){
        Map map = userService.checkCode(phone, clientCode);
        return map;
    }

    //前台:添加个人信息
    @RequestMapping("register")
    public Map register(User user){
        Map map = userService.register(user);
        return map;
    }

    //前台:用户修改个人信息
    @RequestMapping("upDate")
    public Map upDate(User user){
        Map map = userService.ipDate(user);
        return map;
    }
    //金刚道友随即展示五条数据
    @RequestMapping("queryByRand")
    public List<User> queryByRand(String id){
        List<User> users = userService.queryByRand(id);
        return users;
    }
}