package com.baizhi.test;

import com.baizhi.Cmfz;
import com.baizhi.entity.Banner;
import com.baizhi.servive.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = Cmfz.class)
public class BannerTest {
    @Autowired
    BannerService bannerService;

    //测试分页查询
    @Test
    public void name() {
        List<Banner> banners = bannerService.queryAllByLimit(1, 2);
        for (Banner banner : banners) {
            System.out.println("banner = " + banner);
        }
    }
}
