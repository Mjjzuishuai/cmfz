package com.baizhi.servive;

import com.baizhi.entity.Mylog;
import com.baizhi.dao.MylogDao;
import com.baizhi.servive.MylogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Mylog)表服务实现类
 *
 * @author makejava
 * @since 2019-12-29 09:44:27
 */
@Service
@Transactional
public class MylogServiceImpl implements MylogService {
    @Autowired
    private MylogDao mylogDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Mylog> slectAll() {
        List<Mylog> mylogs = mylogDao.selectAll();
        return mylogs;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insert(Mylog mylog) {
        mylogDao.insert(mylog);
    }
}