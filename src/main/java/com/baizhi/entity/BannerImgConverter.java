package com.baizhi.entity;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class BannerImgConverter extends StringImageConverter {
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        //获取服务器上的绝对路径
        String property = System.getProperty("user.dir");
        String[] split = value.split("/");
        String s = split[split.length - 1];
        String url = property+"\\src\\main\\webapp\\back\\img\\"+s;
        System.out.println(url);
        return new CellData(FileUtils.readFileToByteArray(new File(url)));
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return cellData.getStringValue();
    }
}
