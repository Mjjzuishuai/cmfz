package com.baizhi.test;


import com.baizhi.Cmfz;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cmfz.class)
public class PoiTest {
    @Autowired
    BannerDao bannerDao;

    //导出poi
    @Test
    public void name() {
        //new 表格对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //通过表格对象New工作簿对象
        HSSFSheet sheet = workbook.createSheet();
        //通过工作簿对象New行对象
        HSSFRow row = sheet.createRow(0);
        //给第一行（0表示第一行，1表示第二行）单元格设置表头信息
        String[] str = {"id", "标题", "路径", "链接", "创建时间", "描述", "状态"};
        for (int i = 0; i < str.length; i++) {
            String s = str[i];//拿到表头中的每个值
            HSSFCell cell = row.createCell(i);//通过行对象创建单元格对象
            cell.setCellValue(s);//往单元格插入表头中的信息
        }
        //遍历数据库中的数据
        List<Banner> banners = bannerDao.queryAll(null);
        //把数据库中的每一条数据插入到每一行中
        for (int i = 0; i < banners.size(); i++) {
            Banner banner = banners.get(i);
            HSSFRow row1 = sheet.createRow(i + 1);
            HSSFCell cell0 = row1.createCell(0);
            HSSFCell cell1 = row1.createCell(1);
            HSSFCell cell2 = row1.createCell(2);
            HSSFCell cell3 = row1.createCell(3);
            HSSFCell cell4 = row1.createCell(4);
            HSSFCell cell5 = row1.createCell(5);
            HSSFCell cell6 = row1.createCell(6);

            cell0.setCellValue(banner.getId());
            cell1.setCellValue(banner.getTitle());
            cell2.setCellValue(banner.getUrl());
            cell3.setCellValue(banner.getHref());
            cell4.setCellValue(banner.getCreateDate());
            cell5.setCellValue(banner.getDes());
            cell6.setCellValue(banner.getStatus());
        }
        //将Excle文档本地输出
        try {
            //不能操作打开中的文件
            workbook.write(new File("F:\\baizhi\\第三阶段学习\\后期项目\\day7-poiEasyExcel\\自己示例\\" + new Date().getTime() + ".xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
