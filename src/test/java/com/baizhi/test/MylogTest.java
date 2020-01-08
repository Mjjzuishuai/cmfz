package com.baizhi.test;

import com.baizhi.Cmfz;
import com.baizhi.controller.MylogController;
import com.baizhi.entity.Mylog;
import com.baizhi.servive.MylogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cmfz.class)
public class MylogTest {
    @Autowired
    MylogService mylogService;
    @Autowired
    MylogController mylogController;

    @Test
    public void testInset() {

        Mylog mylog = new Mylog("1", "admin", new Date(), "添加了一条数据", "success");
        mylogService.insert(mylog);
    }

    @Test
    public void testSelectALL() {
        List<Mylog> mylogs = mylogController.selectAll();
        for (Mylog mylog : mylogs) {
            System.out.println("mylog = " + mylog);
        }
    }
}
