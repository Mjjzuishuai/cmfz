package com.baizhi.servive;

import com.baizhi.entity.Banner;
import java.util.List;

/**
 * (Banner)表服务接口
 *
 * @author makejava
 * @since 2019-12-26 10:57:32
 */
public interface BannerService {
    //查询所有
    public List<Banner> queryAll(Integer rows) ;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Banner queryById(String id);


    List<Banner> queryAllByLimit(int page, int rows);

    /**
     * 新增数据
     *
     * @param banner 实例对象
     * @return 实例对象
     */
    Banner insert(Banner banner);

    /**
     * 修改数据
     *
     * @param banner 实例对象
     * @return 实例对象
     */
    Banner update(Banner banner);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    //查询一共有多少页
    public Integer queryTotalPage(Integer rows);
    //查询一共有多少条数据
    public Integer queryTotalCount();
}