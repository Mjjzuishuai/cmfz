package com.baizhi.dao;

import com.baizhi.entity.Guru;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Guru)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-30 19:11:32
 */
public interface GuruDao extends Mapper<Guru> {

}