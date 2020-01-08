package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.entity.ChapterDto;
import com.baizhi.servive.ChapterService;
import com.baizhi.util.HttpUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Chapter)表控制层
 *
 * @author makejava
 * @since 2019-12-28 15:10:43
 */
@RestController
@RequestMapping("chapter")
public class ChapterController {
    /**
     * 服务对象
     */
    @Autowired
    private ChapterService chapterService;


    //分页查询所有
    @RequestMapping("selectByPage")
    public ChapterDto selectByPage(Integer page, Integer rows,Chapter chapter) {
        ChapterDto chapterDto = new ChapterDto();
        List<Chapter> chapters = chapterService.selectByPage(page, rows,chapter);//分页查询
        Integer totalCount = chapterService.selectTotalCount();//查询一共多少行
        Integer integer = chapterService.selectTotalPage(rows);//查询一共多少页
        ChapterDto chapterDto1 = chapterDto.setPage(page).setTotal(integer).setRecords(totalCount).setRows(chapters);
        return chapterDto1;
    }


    //jqgrid发ajax增删改
    @RequestMapping("edit")
    public Map<String, String> edit(Chapter chapter, String oper) {
        if (oper.equals("edit")) {
            chapterService.upDate(chapter);
        } else if (oper.equals("add")) {
            chapterService.insert(chapter);
        } else {
            chapterService.delete(chapter.getId());
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("chapterId",chapter.getId());
        return map;
    }

    //文件上传
    @RequestMapping("upLoad")
    public Map<String,String> upLoad(HttpServletRequest request, MultipartFile url, String chapterId) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        //获取存储文件的服务器路径,并判断此路径存不存在，不存在则创建一个
        String realPath = request.getSession().getServletContext().getRealPath("/back/music/");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        //调用工具类获取全路径
        String http = HttpUtil.getHttp(url, request, "/back/music/");
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);//存id
        chapter.setUrl(http);//往服务器存全路径
        // 计算文件大小
        Double size = Double.valueOf(url.getSize()/1024/1024);
        chapter.setSize(size);
        // 计算音频时长
        // 使用三方计算音频时间工具类 得出音频时长
        String[] split = http.split("/");
        // 获取文件名
        String name = split[split.length-1];
        // 通过文件获取AudioFile对象 音频解析对象
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        // 通过音频解析对象 获取 头部信息 为了信息更准确 需要将AudioHeader转换为MP3AudioHeader
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        // 获取音频时长 秒
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength/60 + "分" + trackLength%60 + "秒";
        chapter.setTime(time);
        chapterService.upDate(chapter);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }

    //文件下载
    @RequestMapping("downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        // 处理url路径 找到文件
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/back/music/");
        String name = split[split.length-1];
        File file = new File(realPath, name);
        // 调用该方法时必须使用 location.href 不能使用ajax ajax不支持下载
        // 通过url获取本地文件
        response.setHeader("Content-Disposition", "attachment; filename="+name);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
        // FileUtils.copyFile("服务器文件",outputStream)
        //FileUtils.copyFile();
    }
}