package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.AlbumPageDto;
import com.baizhi.entity.Banner;
import com.baizhi.servive.AlbumService;
import com.baizhi.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Album)表控制层
 *
 * @author makejava
 * @since 2019-12-27 15:41:30
 */
@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    //分页查所有
    @RequestMapping("queryByPage")
    public AlbumPageDto queryByPage(Integer page, Integer rows) {
        //new一个dto对象
        AlbumPageDto albumPageDto = new AlbumPageDto();
        //查询一共多少行数据
        Integer totalCount = albumService.selectTotalCount();
        //查询一共多少页
        Integer integer = albumService.selectTotalPage(rows);
        //分页查询
        List<Album> albums = albumService.selectByPage(page, rows);
        AlbumPageDto albumPageDto1 = albumPageDto.setPage(page).setTotal(integer).setRecords(totalCount).setRows(albums);
        return albumPageDto1;
    }

    //根据id查一个
    public Album selectOne(String id){
        Album album = albumService.selectOne(id);
        return album;
    }

    //增删改
    @RequestMapping("edit")
    public Map<String, String> edit(Album album, String oper) {
        if (oper.equals("edit")) {
            albumService.upDate(album);
        } else if (oper.equals("add")) {
            albumService.insert(album);
        } else {
            albumService.delete(album.getId());
        }
        String id = album.getId();
        Map<String, String> map = new HashMap<>();
        map.put("albumId", id);
        return map;
    }

    //文件上传
    @RequestMapping("upLoad")
    public void upLoad(MultipartFile cover, HttpServletRequest request, String albumId){
        //获取文件要上传的路径
        String realPath = request.getSession().getServletContext().getRealPath("/back/img/");
        //判断此路径存不存在，不存在则创建一个
        java.io.File file1 = new java.io.File(realPath);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        String http = HttpUtil.getHttp(cover, request, "/back/img/");
        //根据bannerId获得此对象
        Album album = albumService.selectOne(albumId);
        album.setCover(http);
        //调用service放发修改数据库url
        albumService.upDate(album);
    }

    //前台：专辑详情接口（根据id查询一个）
    @RequestMapping("queryOneAlbum")
    public Map queryOneAlbum(String uid, String id){
        Map map = albumService.queryOneAlbum(uid, id);
        return map;
    }
}