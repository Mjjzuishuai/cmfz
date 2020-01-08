package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Article)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-30 16:42:40
 */
public interface ArticleDao extends Mapper<Article> { }