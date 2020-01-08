package com.baizhi.test;

import com.baizhi.Cmfz;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.entity.UserLocation;
import com.baizhi.servive.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cmfz.class)
public class UserTest {
    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;


    //查询注册时间
    @Test
    public void name() {
        Integer integer = userDao.selectByRegisterTime("女", 1);
        System.out.println(integer);
    }


    //测试分页查所有
    @Test
    public void test01() {
        List<User> users = userService.selectByPage(1, 2);
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }
    //测试增删改

    @Test
    public void test02() {
        /*User user = new User();
        user.setId("7");
        userService.insert(user);*/
      /*  String[] ids = {"7"};
        userService.deleteByIds(ids);*/
        User user = new User();
        user.setId("4");
        user.setSex("男");
        userService.ipDate(user);
    }

    //查询每个地区男女的注册人数
    @Test
    public void test03() {
        List<UserLocation> list = userDao.selectByLocation("男");
        for (UserLocation userLocation : list) {
            System.out.println("userLocation = " + userLocation);
        }
    }
}
