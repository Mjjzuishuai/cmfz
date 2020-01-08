package com.baizhi.dao;

import com.baizhi.entity.Counter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Counter)表数据库访问层
 *
 * @author makejava
 * @since 2020-01-06 21:09:05
 */

public interface CounterDao extends Mapper<Counter> {



}