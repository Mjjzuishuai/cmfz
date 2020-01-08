package com.baizhi.servive;

import com.baizhi.entity.Article;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;

/**
 * (Article)表服务接口
 *
 * @author makejava
 * @since 2019-12-30 16:42:40
 */
public interface ArticleService {
    //分页查所有
    public List<Article> selectByPage(Integer page,Integer pagesize);
    //根据id查一个
    public Article selectOne(String id);
    //添加一个
    public void insert(Article article);
    //修改一个
    public void upDate(Article article);
    //删除一个
    public void delete(String id);
    //查询有多少行数据
    public Integer selectCount();
    //查询一共有多少页
    public Integer selectTotalPage(Integer pagesize);
    //前台：展示文章详情(根据id查询一个文章)
    public Map queryOneArticle(String uid, String id);

}