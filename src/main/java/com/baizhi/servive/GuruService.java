package com.baizhi.servive;

import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import java.util.List;
import java.util.Map;

/**
 * (Guru)表服务接口
 *
 * @author makejava
 * @since 2019-12-30 19:11:32
 */
public interface GuruService {
   //前台:查询所有（不分页）
   public Map selectAll();
   //分页
    public List<Guru> selectByPage(Integer page,Integer pagesize);
    //根据id差一个
    public Guru selectOne(String id);
    //曾
    public void insert(Guru guru);
    //删
    public void delete(String id);
    //改
    public void upDate(Guru guru);
    //查总行数
    public Integer selectCount();
    //查询一共有多少页
    public Integer selectTotalPage(Integer pagesize);
   //后台:查询所有大师
   public List<Guru> queryAll();
}