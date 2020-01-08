package com.baizhi.test;


import com.baizhi.Cmfz;
import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import com.baizhi.servive.GuruService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cmfz.class)
public class GuruTest {
    @Autowired
    GuruService guruService;


    @Test
    public void testPage() {
        List<Guru> gurus = guruService.selectByPage(1, 2);
        for (Guru guru : gurus) {
            System.out.println("guru = " + guru);
        }
    }

    @Test
    public void testTotalPage() {
        Integer integer = guruService.selectTotalPage(2);
        System.out.println("integer = " + integer);
    }

    @Test
    public void testInset() {
        Guru guru = new Guru();
        guru.setId("5");
        guruService.insert(guru);
    }
}
