package com.baizhi.test;

import com.baizhi.Cmfz;
import com.baizhi.entity.Article;
import com.baizhi.servive.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = Cmfz.class)
public class ArticleTest {
    @Autowired
    ArticleService articleService;

    @Test
    public void testPage() {
        List<Article> articles = articleService.selectByPage(1, 2);
        for (Article article : articles) {
            System.out.println("article = " + article);
        }
    }

    @Test
    public void testTotalPage() {
        Integer integer = articleService.selectTotalPage(2);
        System.out.println("integer = " + integer);
    }

    @Test
    public void testInset() {
        Article article = new Article();
        article.setId("5");
        articleService.insert(article);
    }
}
