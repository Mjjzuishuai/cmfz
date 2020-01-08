package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * (Banner)实体类
 *
 * @author makejava
 * @since 2019-12-26 10:57:32
 */
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Banner implements Serializable {
    private static final long serialVersionUID = -91981836126050974L;

    @ExcelProperty(value = "ID")
    private String id;
    @ExcelProperty(value = "标题")
    private String title;
    @ExcelProperty(value = "图片",converter = BannerImgConverter.class)
    private String url;
    @ExcelProperty(value = "链接")
    private String href;
    @Column(name = "create_date")
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("创建时间")
    private Date createDate;
    @ExcelProperty(value = "描述")
    private String des;
    @ExcelProperty(value = "状态")
    private String status ;
}