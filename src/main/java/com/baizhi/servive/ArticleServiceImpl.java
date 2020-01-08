package com.baizhi.servive;

import com.baizhi.entity.Article;
import com.baizhi.dao.ArticleDao;
import com.baizhi.servive.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * (Article)表服务实现类
 *
 * @author makejava
 * @since 2019-12-30 16:42:40
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Article> selectByPage(Integer page, Integer pagesize) {
        List<Article> articles = articleDao.selectByRowBounds(null, new RowBounds((page - 1) * pagesize, pagesize));
        return articles;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Article selectOne(String id) {
        Article article = articleDao.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public void insert(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        article.setPublishDate(new Date());
        article.setStatus("正常");
        articleDao.insert(article);
    }

    @Override
    public void upDate(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public void delete(String id) {
        articleDao.deleteByPrimaryKey(id);
    }
    //查询一共有多少行数据
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public Integer selectCount() {
        int i = articleDao.selectCount(null);
        return i;
    }

    //查询一共有多少页
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public Integer selectTotalPage(Integer pagesize) {
        int i = articleDao.selectCount(null);
        Integer totalPage;
        totalPage=i%pagesize==0?i/pagesize:i/pagesize+1;
        return totalPage;
    }

    @Override
    //前台：展示文章详情(根据id查询一个文章)
    public Map queryOneArticle(String uid, String id) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            Article article = articleDao.selectByPrimaryKey(id);
            map.put("status","200");
            map.put("article",article);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("status","-200");
            return map;
        }
    }
}