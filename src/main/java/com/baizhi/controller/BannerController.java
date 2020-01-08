package com.baizhi.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.entity.Banner;
import com.baizhi.entity.BannerDataListener;
import com.baizhi.entity.BannerPageDto;
import com.baizhi.servive.BannerService;
import com.baizhi.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * (Banner)表控制层
 *
 * @author makejava
 * @since 2019-12-26 10:57:32
 */
@RestController
@RequestMapping("banner")
public class BannerController {
    /**
     * 服务对象
     */
    @Autowired
    private BannerService bannerService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Banner selectOne(String id) {
        return this.bannerService.queryById(id);
    }


    //后台:jqGrid分页查询
    @RequestMapping("queryBypage")
    public BannerPageDto queryBypage(Integer page, Integer rows) {
        if (page == null) {
            page = 1;
        }
        //传递的数据
        List<Banner> list = bannerService.queryAllByLimit(page, rows);
        //总页数
        Integer totalPage = bannerService.queryTotalPage(rows);
        Integer totalCount = bannerService.queryTotalCount();
        BannerPageDto dto = new BannerPageDto();
        dto.setPage(page);
        dto.setTotal(totalPage);
        dto.setRecords(totalCount);
        dto.setRows(list);
        System.out.println(dto);
        return dto;
    }

    //jqGird发ajax增删改查
    @RequestMapping("modify")
    public Map<String, String> modify(Banner banner, String oper) {
        if ("add".equals(oper)) {
            banner.setId(UUID.randomUUID().toString());
            bannerService.insert(banner);
        } else if ("edit".equals(oper)) {
            bannerService.update(banner);
        } else {
            bannerService.deleteById(banner.getId());
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("bannerId", banner.getId());
        return map;
    }

    //文件上传
    @RequestMapping("upLoad")
    public void upLoad(MultipartFile url, HttpServletRequest request, String bannerId) {
        //获取文件要上传的路径
        String realPath = request.getSession().getServletContext().getRealPath("/back/img");
        //判断此路径存不存在，不存在则创建一个
        java.io.File file1 = new java.io.File(realPath);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        String http = HttpUtil.getHttp(url, request, "/back/img/");

        //根据bannerId获得此对象
        Banner banner = bannerService.queryById(bannerId);
        banner.setUrl(http);

        //调用service放发修改数据库url
        bannerService.update(banner);


    }

    //导出轮播图信息
    @RequestMapping("out")
    public void out(HttpServletResponse response) throws IOException {
        List<Banner> banners = bannerService.queryAll(null);

        //写下边四步是为了让下载的文件以xls形式下载，不然会以zip包下载
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String encode = URLEncoder.encode("轮播图信息", "UTF-8");
        String fileName = encode.replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), Banner.class)
                .sheet("轮播图信息")
                .doWrite(banners);
    }


    //导出轮播图信息模版
    @RequestMapping("outIn")
    public void outIn(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String encode = URLEncoder.encode("轮播图信息", "UTF-8");
        String fileName = encode.replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Banner.class)
                .sheet("轮播图信息").doWrite(new ArrayList());
    }
    //导入轮播图信息
    @RequestMapping("in")
    public void in(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Banner.class, new BannerDataListener()).sheet().doRead();
    }
}