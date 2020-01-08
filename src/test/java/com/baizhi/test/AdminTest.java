package com.baizhi.test;

import com.baizhi.Cmfz;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootTest(classes = Cmfz.class)
@RunWith(value = SpringRunner.class)
public class AdminTest {
    @Autowired
    AdminDao adminDao;

    @Test
    public void testSelectOne() {
        Admin admin = new Admin("1", "admin", "admin");
        Admin admin1 = adminDao.selectOne(admin);
        System.out.println("admin1 = " + admin1);
    }
}
