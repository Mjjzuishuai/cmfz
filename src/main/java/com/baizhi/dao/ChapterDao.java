package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Chapter)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-28 15:10:43
 */
public interface ChapterDao extends Mapper<Chapter> {



}