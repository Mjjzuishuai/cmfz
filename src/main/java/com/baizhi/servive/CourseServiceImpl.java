package com.baizhi.servive;

import com.baizhi.entity.Course;
import com.baizhi.dao.CourseDao;
import com.baizhi.servive.CourseService;
import javafx.print.PageOrientation;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import javax.annotation.Resource;
import java.util.*;

/**
 * (Course)表服务实现类
 *
 * @author makejava
 * @since 2020-01-06 20:23:20
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Map selectAll(String uid) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            Course course = new Course();
            course.setUserId(uid);
            List<Course> courses = courseDao.select(course);
            map.put("status", "200");
            map.put("course", courses);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "-200");
            return map;
        }
    }

    //添加功课
    @Override
    public Map insert(Course course) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            course.setId(UUID.randomUUID().toString());
            course.setCreateDate(new Date());
            courseDao.insert(course);
            map.put("status", "200");
            map.put("coutse", course);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "-200");
            return map;
        }
    }

    //前台:根据id删除一门功课
    @Override
    public Map delete(String id) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            Course course = courseDao.selectByPrimaryKey(id);
            courseDao.deleteByPrimaryKey(id);
            map.put("status", "200");
            map.put("course", course);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "-200");
            return map;
        }
    }
}