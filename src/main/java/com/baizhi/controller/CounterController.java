package com.baizhi.controller;

import com.baizhi.entity.Counter;
import com.baizhi.servive.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * (Counter)表控制层
 *
 * @author makejava
 * @since 2020-01-06 21:09:05
 */
@RestController
@RequestMapping("counter")
public class CounterController {
    /**
     * 服务对象
     */
    @Autowired
    private CounterService counterService;

    //前台:根据功课id查询出其所对应的所有计数器
    @RequestMapping("selectAll")
    public Map selectAll(String courseId){
        Map map = counterService.selectAll(courseId);
        return map;
    }

    //前台：添加一个计数器
    @RequestMapping("insert")
    public Map insert(Counter counter){
        Map map = counterService.insert(counter);
        return map;
    }

    //前台:根据id删除一个
    @RequestMapping("delete")
    public Map delete(String id, String courseId){
        Map map = counterService.delete(id, courseId);
        return map;
    }

    //前台：修改一个计数器
    @RequestMapping("upDate")
    public Map upDate(Counter counter){
        Map map = counterService.upDate(counter);
        return map;
    }
}