package com.baizhi.servive;

import com.baizhi.entity.Counter;
import com.baizhi.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * (Counter)表服务接口
 *
 * @author makejava
 * @since 2020-01-06 21:09:05
 */
public interface CounterService {

    //根据功课Id查询出该功课对应的所有计数器
    public Map selectAll(String courseId);
    //添加计数器
    public Map insert(Counter counter);
    //删除计数器
    public Map delete(String id,String courseId);
    //修改计数器
    public Map upDate(Counter counter);
}