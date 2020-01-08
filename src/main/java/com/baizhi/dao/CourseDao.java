package com.baizhi.dao;

import com.baizhi.entity.Course;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Course)表数据库访问层
 *
 * @author makejava
 * @since 2020-01-06 20:23:20
 */
public interface CourseDao extends Mapper<Course> {
}