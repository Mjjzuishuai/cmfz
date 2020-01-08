package com.baizhi.servive;

import com.baizhi.entity.Mylog;
import java.util.List;

/**
 * (Mylog)表服务接口
 *
 * @author makejava
 * @since 2019-12-29 09:44:27
 */
public interface MylogService {

    //查所有
    public List<Mylog> slectAll();
    //添加一条数据
    public void insert(Mylog mylog);
}