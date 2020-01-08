package com.baizhi.servive;

import com.baizhi.aspect.LogAnnotation;
import com.baizhi.entity.Album;
import com.baizhi.dao.AlbumDao;
import com.baizhi.servive.AlbumService;
import joptsimple.internal.Rows;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.genid.GenId;

import javax.annotation.Resource;
import java.util.*;


@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {


    @Autowired
    AlbumDao albumDao;


    //分页查所有
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Album> selectByPage(Integer page, Integer rows) {
        Integer beginRow = (page - 1) * rows;
        List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(beginRow, rows));
        return albums;
    }

    //根据id查询一条专辑
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Album selectOne(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }

    //添加一个专辑
    @Override
    @LogAnnotation(value = "添加了一部专辑")
    public void insert(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCreateDate(new Date());
        album.setStatus("正常");
        albumDao.insert(album);
    }

    //修改一条专辑
    @Override
    @LogAnnotation(value = "修改了一部专辑")
    public void upDate(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    //删除一条专辑
    @Override
    @LogAnnotation(value = "删除了一部专辑")
    public void delete(String id) {
        albumDao.deleteByPrimaryKey(id);
    }


    //查询一共有多少条数据
    @Override
    public Integer selectTotalCount() {
        int i = albumDao.selectCount(null);
        return i;
    }

    //查询一共有多少页
    @Override
    public Integer selectTotalPage(Integer rows) {
        Integer pageSize;
        //查询一共有多少条数据
        int i = albumDao.selectCount(null);
        //判断有多少页
        pageSize = i % rows == 0 ? i / rows : i / rows + 1;
        return pageSize;
    }

    //前台：专辑详情接口（根据id查询一个）
    public Map queryOneAlbum(String uid, String id) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            Album album = albumDao.selectByPrimaryKey(id);
            map.put("status", "200");
            map.put("album", album);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "-200");
            return map;
        }
    }
}