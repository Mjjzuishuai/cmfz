package com.baizhi.test;

import com.baizhi.Cmfz;
import com.baizhi.entity.Chapter;
import com.baizhi.servive.ChapterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cmfz.class)
public class ChapterTest {
    @Autowired
    ChapterService chapterService;

   /* @Test
    public void testPage() {
        List<Chapter> chapters = chapterService.selectByPage(1, 2);
        for (Chapter chapter : chapters) {
            System.out.println("chapter = " + chapter);
        }
    }*/

    @Test
    public void testTotalCount() {
        Integer totalCount = chapterService.selectTotalCount();
        System.out.println("totalCount = " + totalCount);
    }

    @Test
    public void testTotalPgge() {
        Integer integer = chapterService.selectTotalPage(2);
        System.out.println("integer = " + integer);
    }

    @Test
    public void testInsert() {
        Chapter chapter = new Chapter("3", "daf", "dasf1", 10.0, "的萨法", new Date(), "1");
        chapterService.insert(chapter);
    }

    @Test
    public void testDelete() {
        chapterService.delete("3");
    }

    @Test
    public void testUpdate() {
        Chapter chapter = new Chapter("2", "daf", "dasf1", 10.0, "的萨法", new Date(), "1");
        chapterService.upDate(chapter);
    }
}
