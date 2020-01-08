package com.baizhi.servive;

import com.baizhi.aspect.LogAnnotation;
import com.baizhi.entity.Banner;
import com.baizhi.dao.BannerDao;
import com.baizhi.servive.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * (Banner)表服务实现类
 *
 * @author makejava
 * @since 2019-12-26 10:57:32
 */
@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    //依赖注入dao
    @Autowired
    private BannerDao bannerDao;


    //查询所有数据
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> queryAll(Integer rows) {
        List<Banner> banners = bannerDao.queryAll(null);

        return banners;
    }
    //查询一gong多少页
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer queryTotalPage(Integer rows) {
        Integer totalPage;
        Integer totalCount = bannerDao.queryTotalCount();
        if(totalCount%rows == 0 ){
            totalPage = totalCount / rows ;
        }else {
            totalPage = totalCount / rows + 1 ;
        }
        return totalPage;
    }

    //查询一共多少条数据
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer queryTotalCount() {
        Integer integer = bannerDao.queryTotalCount();
        return integer;
    }



    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    //通过id查询一个
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Banner queryById(String id) {
        return this.bannerDao.queryById(id);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> queryAllByLimit(int page, int pagesize) {
        Integer offset = (page-1)*pagesize;
        Integer limit =page*pagesize;
        return this.bannerDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param banner 实例对象
     * @return 实例对象
     */
    //添加一个
    @Override
    @LogAnnotation(value = "添加了一条轮播图")
    public Banner insert(Banner banner) {
//        banner.setId(UUID.randomUUID().toString());
        banner.setCreateDate(new Date());
        banner.setStatus("激活");
        this.bannerDao.insert(banner);
        return banner;
    }

    /**
     * 修改数据
     *
     * @param banner 实例对象
     * @return 实例对象
     */
    @Override
    @LogAnnotation(value = "修改了一条轮播图")
    public Banner update(Banner banner) {
        this.bannerDao.update(banner);
        return this.queryById(banner.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @LogAnnotation(value = "删除了一条轮播图")
    public boolean deleteById(String id) {

        return this.bannerDao.deleteById(id)>0;
    }
}