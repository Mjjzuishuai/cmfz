package com.baizhi.servive;

import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import com.baizhi.dao.GuruDao;
import com.baizhi.servive.GuruService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.*;

/**
 * (Guru)表服务实现类
 *
 * @author makejava
 * @since 2019-12-30 19:11:32
 */
@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    private GuruDao guruDao;

    @Override
    public Map selectAll() {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            List<Guru> gurus = guruDao.selectAll();
            map.put("status", "200");
            map.put("list", gurus);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "-200");
            return map;
        }

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Guru> selectByPage(Integer page, Integer pagesize) {
        List<Guru> gurus = guruDao.selectByRowBounds(null, new RowBounds((page - 1) * pagesize, pagesize));
        return gurus;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Guru selectOne(String id) {
        Guru guru = guruDao.selectByPrimaryKey(id);
        return guru;
    }

    @Override
    public void insert(Guru guru) {
        guru.setId(UUID.randomUUID().toString());
        guru.setStatus("正常");
        guruDao.insert(guru);
    }

    @Override
    public void delete(String id) {
        guruDao.deleteByPrimaryKey(id);
    }

    @Override
    public void upDate(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Integer selectCount() {
        int i = guruDao.selectCount(null);
        return i;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Integer selectTotalPage(Integer pagesize) {
        int i = guruDao.selectCount(null);
        Integer totalPage;
        totalPage = i % pagesize == 0 ? i / pagesize : i / pagesize + 1;
        return totalPage;
    }

    //后台：查询所有大师
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Guru> queryAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }
}