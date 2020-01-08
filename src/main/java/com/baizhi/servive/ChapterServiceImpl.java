package com.baizhi.servive;

import com.baizhi.aspect.LogAnnotation;
import com.baizhi.entity.Chapter;
import com.baizhi.dao.ChapterDao;
import com.baizhi.servive.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * (Chapter)表服务实现类
 *
 * @author makejava
 * @since 2019-12-28 15:10:43
 */
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;



    //分页查所有
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Chapter> selectByPage(Integer page, Integer pagesize,Chapter chapter) {
        RowBounds rowBounds = new RowBounds((page - 1) * pagesize, pagesize);
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, rowBounds);
        return chapters;
    }


    //根据id查一个
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Chapter selectOne(String id) {
        Chapter chapter = chapterDao.selectByPrimaryKey(id);
        return chapter;
    }


    //添加一个
    @Override
    @LogAnnotation(value = "添加一条音频")
    public void insert(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setCreateTime(new Date());
        chapterDao.insert(chapter);
    }


    //修改一个
    @Override
    @LogAnnotation(value = "修改了一条音频")
    public void upDate(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }


    //通过id删除一个
    @Override
    @LogAnnotation(value = "删除了一条音频")
    public void delete(String id) {
        chapterDao.deleteByPrimaryKey(id);
    }

    //查询一共多少条数据
    @Override
    public Integer selectTotalCount() {
        int i = chapterDao.selectCount(null);
        return i;
    }
    //查询一共有多少页
    @Override
    public Integer selectTotalPage(Integer pagesize) {
        Integer totalPage;
        int i = chapterDao.selectCount(null);
        totalPage=i%pagesize==0?i/pagesize:i/pagesize+1;
        return totalPage;
    }
}