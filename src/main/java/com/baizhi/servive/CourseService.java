package com.baizhi.servive;

import com.baizhi.dao.CourseDao;
import com.baizhi.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Map;


public interface CourseService {

    //很据userId查询出该用户的所有功课
    public Map selectAll(String uid);
    //添加一门功课
    public Map insert(Course course);
    //删除一门功课
    public Map delete(String id);
}