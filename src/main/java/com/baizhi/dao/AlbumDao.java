package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (Album)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-27 15:41:30
 */
public interface AlbumDao extends Mapper<Album> {


}