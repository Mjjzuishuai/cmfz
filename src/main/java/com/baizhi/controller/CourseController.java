package com.baizhi.controller;

import com.baizhi.entity.Course;
import com.baizhi.servive.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * (Course)表控制层
 *
 * @author makejava
 * @since 2020-01-06 20:23:20
 */
@RestController
@RequestMapping("course")
public class CourseController {
    /**
     * 服务对象
     */
    @Autowired
    private CourseService courseService;


    //前台:根据用户id查询出来他所对应的所有课程
    @RequestMapping("selectAll")
    public Map selectAll(String uid){
        Map map = courseService.selectAll(uid);
        return map;
    }

    //前台:添加功课
    @RequestMapping("insert")
    public Map insert(Course course) {
        Map map = courseService.insert(course);
        return map;
    }

    //前台:删除一门功课
    @RequestMapping("delete")
    public Map delete(String id){
        Map map = courseService.delete(id);
        return map;
    }
}