package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.entity.ArticlePageDto;
import com.baizhi.servive.ArticleService;
import com.baizhi.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.aspectj.weaver.ast.Var;
import org.junit.platform.commons.function.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (Article)表控制层
 *
 * @author makejava
 * @since 2019-12-30 16:42:40
 */
@RestController
@RequestMapping("article")
public class ArticleController {
    /**
     * 服务对象
     */
    @Autowired
    private ArticleService articleService;

    //分页茶所有
    @RequestMapping("selectByPage")
    public ArticlePageDto selectByPage(Integer page, Integer rows) {
        ArticlePageDto articlePageDto = new ArticlePageDto();
        List<Article> articles = articleService.selectByPage(page, rows);
        Integer integer = articleService.selectCount();
        Integer integer1 = articleService.selectTotalPage(rows);
        articlePageDto.setPage(page);
        articlePageDto.setRecords(integer);
        articlePageDto.setRows(articles);
        articlePageDto.setTotal(integer1);
        return articlePageDto;
    }

    //根据id查一个
    @RequestMapping("selectOne")
    public Article selectOne(String id){
        Article article = articleService.selectOne(id);
        return article;
    }
    //增改
    @RequestMapping("edit")
    public String edit(Article article, MultipartFile file, HttpServletRequest request) {
        //获取文件要上传的路径
        String realPath = request.getSession().getServletContext().getRealPath("/back/img/");
        System.out.println(file);
        //判断此路径存不存在，不存在则创建一个
        java.io.File file1 = new java.io.File(realPath);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        String http = HttpUtil.getHttp(file, request, "/back/img/");
        article.setImg(http);
        if(article.getId()==null || "".equals(article.getId())){
            articleService.insert(article);
        }else {
            articleService.upDate(article);
        }
        return "ok";
    }

    //根据id删除
    @RequestMapping("delete")
    public void delete(String id){
        System.out.println("id = " + id);
        articleService.delete(id);
    }


    //复文本编辑器上传文件
    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpServletRequest request) {
        HashMap map = new HashMap<>();
        //获取服务器路径
        String realPath = request.getSession().getServletContext().getRealPath("/back/img/");
        //判断路径是否存在，不存在则创建一个
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //调用工具类获取全路径

        try {
            String http = HttpUtil.getHttp(imgFile, request, "/back/img/");
            map.put("error",0);
            map.put("url", http);
        } catch (Exception e) {
            map.put("error",1);
            e.printStackTrace();
        }
        return map;
    }


    //富文本编辑器选择文件时候，展示所有（服务器上）图片
    @RequestMapping("showAllImg")
    public Map showAllImg(HttpServletRequest request, HttpSession session){
        //new一个Map
        HashMap hashMap = new HashMap();

        //put一个文件夹路径
        hashMap.put("current_url",request.getContextPath()+"/back/img/");

        //put一个文件夹长度
        String realPath = session.getServletContext().getRealPath("/back/img/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count",files.length);

        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            // 通过字符串拆分获取时间戳
            String time = name.split("_")[0];
            // 创建SimpleDateFormat对象 指定yyyy-MM-dd hh:mm:ss 样式
            //  simpleDateFormat.format() 获取指定样式的字符串(yyyy-MM-dd hh:mm:ss)
            // format(参数)  参数:时间类型   new Date(long类型指定时间)long类型  现有数据:字符串类型时间戳
            // 需要将String类型 转换为Long类型 Long.valueOf(str);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        return hashMap;
    }
    //前台：展示文章详情(根据id查询一个文章)
    @RequestMapping("queryOneArticle")
    public Map queryOneArticle(String uid,String id){
        Map map = articleService.queryOneArticle(uid, id);
        return map;
    }
}