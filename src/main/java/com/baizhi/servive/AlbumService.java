package com.baizhi.servive;

import com.baizhi.entity.Album;
import java.util.List;
import java.util.Map;


public interface AlbumService {
    //分页查所有
    public List<Album> selectByPage(Integer page,Integer rows);
    //根据Id查询一条专辑
    public Album selectOne(String id);
    //添加
    public void insert(Album album);
    //修改
    public void upDate(Album album);
    //根据id删除
    public void delete(String id);
    //查询有多少一共有多少行数据
    public Integer selectTotalCount();
    //查询一共有多少页
    public Integer selectTotalPage(Integer rows);
    //前台：专辑详情接口（根据id查询一个）
    public Map queryOneAlbum(String uid, String id);
}