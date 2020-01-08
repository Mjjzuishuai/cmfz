package com.baizhi.servive;

import com.baizhi.entity.Counter;
import com.baizhi.dao.CounterDao;
import com.baizhi.servive.CounterService;
import org.junit.platform.commons.function.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * (Counter)表服务实现类
 *
 * @author makejava
 * @since 2020-01-06 21:09:05
 */
@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    private CounterDao counterDao;


    //根据功课Id查询出该功课对应的所有计数器
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map selectAll(String courseId) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            Counter counter = new Counter();
            Counter counter1 = counter.setCourseId(courseId);
            List<Counter> counters = counterDao.select(counter1);
            map.put("status", "200");
            map.put("counters", counters);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "-200");
            return map;
        }
    }

    //前台：添加一个计数器
    @Override
    public Map insert(Counter counter) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            counter.setId(UUID.randomUUID().toString());
            counter.setCreateDate(new Date());
            counterDao.insert(counter);
            map.put("status", "200");
            map.put("counter", counter);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "200");
            return map;
        }
    }

    //根据id删除计数器
    public Map delete(String id, String courseId) {
        HashMap<Object, Object> map = new HashMap<>();
        try {

            Counter counter = new Counter();
            Counter counter1 = counter.setCourseId(courseId);

            counterDao.deleteByPrimaryKey(id);
            List<Counter> select = counterDao.select(counter1);
            map.put("status", "200");
            map.put("counters", select);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "-200");
            return map;
        }
    }

    //修改计数器
    @Override
    public Map upDate(Counter counter) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            String courseId = counter.getCourseId();
            Counter counter1 = new Counter();
            Counter counter2 = counter1.setCourseId(courseId);
            List<Counter> counters = counterDao.select(counter2);
            counterDao.updateByPrimaryKeySelective(counter);
            map.put("status","200");
            map.put("counters",counters);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("status","-200");
            return map;
        }
    }
}