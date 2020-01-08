package com.baizhi.controller;


import com.baizhi.entity.Mylog;
import com.baizhi.servive.MylogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Mylog")
public class MylogController {
    @Autowired
    MylogService mylogService;
    //茶所有
    @RequestMapping("selectAll")
    public List<Mylog> selectAll(){
        List<Mylog> mylogs = mylogService.slectAll();
        return mylogs;
    }
}
