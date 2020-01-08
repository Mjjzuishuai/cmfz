package com.baizhi.controller;

import com.baizhi.servive.OnePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("OnePage")
public class OnePageController {
    @Autowired
    OnePageService onePageService;
    //一级页面展示
    public Map showOnePage(String uid,String type,String sub_type){
        Map map = onePageService.onePage(uid, type, sub_type);
        return map;
    }
}
