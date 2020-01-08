package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.entity.ArticlePageDto;
import com.baizhi.entity.Guru;
import com.baizhi.entity.GuruPageDto;
import com.baizhi.servive.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Guru)表控制层
 *
 * @author makejava
 * @since 2019-12-30 19:11:32
 */
@RestController
@RequestMapping("guru")
public class GuruController {
    /**
     * 服务对象
     */
    @Autowired
    private GuruService guruService;
    //查询所有上师（不分页）
    @RequestMapping("selectAll")
    public Map selectAll(){
        Map map = guruService.selectAll();
        return map;
    }
    //分页茶所有
    @RequestMapping("selectByPage")
    public GuruPageDto selectByPage(Integer page, Integer rows) {
        GuruPageDto guruPageDto = new GuruPageDto();
        List<Guru> gurus = guruService.selectByPage(page, rows);
        Integer integer = guruService.selectCount();
        Integer integer1 = guruService.selectTotalPage(rows);
        guruPageDto.setPage(page);
        guruPageDto.setRecords(integer);
        guruPageDto.setRows(gurus);
        guruPageDto.setTotal(integer1);
        return guruPageDto;
    }

    //增删改
    @RequestMapping("edit")
    public Map<String, String> edit(Guru guru, String oper) {
        return null;
    }


    //后台:查询所有大师
    @RequestMapping("queryAll")
    public List<Guru> queryAll(){
        List<Guru> gurus = guruService.queryAll();
        return gurus;
    }
}