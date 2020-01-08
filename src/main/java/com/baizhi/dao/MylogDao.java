package com.baizhi.dao;

import com.baizhi.entity.Mylog;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Mylog)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-29 09:44:27
 */
public interface MylogDao extends Mapper<Mylog> {

}