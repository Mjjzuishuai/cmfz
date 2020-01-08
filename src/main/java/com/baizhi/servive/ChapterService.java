package com.baizhi.servive;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (Chapter)表服务接口
 *
 * @author makejava
 * @since 2019-12-28 15:10:43
 */

public interface ChapterService {

    //分页查询所有
    public List<Chapter> selectByPage(Integer page,Integer pagesize,Chapter chapter);
    //通过id查询一个
    public Chapter selectOne(String id);
    //添加一个
    public void insert(Chapter chapter);
    //修改一个
    public void upDate(Chapter chapter);
    //删除一个
    public void delete(String id);
    //查询一共多少条数据
    public Integer selectTotalCount();
    //查询一共多少页
    public Integer selectTotalPage(Integer pagesize);
}