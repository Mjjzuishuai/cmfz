package com.baizhi.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.dao.BannerDao;
import com.baizhi.util.MyWebWare;

//此类能拿到Excel表格上传的文件数据
public class BannerDataListener extends AnalysisEventListener<Banner>{

    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        Object beanByClass = MyWebWare.getBeanByClass(BannerDao.class);
        BannerDao bannerDao = (BannerDao) beanByClass;
        bannerDao.insert(banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
