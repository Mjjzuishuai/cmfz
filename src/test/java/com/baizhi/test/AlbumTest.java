package com.baizhi.test;


import com.baizhi.Cmfz;
import com.baizhi.entity.Album;
import com.baizhi.servive.AlbumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cmfz.class)
public class AlbumTest {
    @Autowired
    AlbumService albumService;


    @Test
    public void testSelectByPage() {
        List<Album> albums = albumService.selectByPage(1, 2);
        for (Album album : albums) {
            System.out.println("album = " + album);
        }
    }

    @Test
    public void testInsert() {
        Album album = new Album("3", "fdasfd", "adsf", "dasf", "sdaf", 2, "dasf", "adsfas", new Date(),"dsaf");
        albumService.insert(album);
    }

    @Test
    public void testCount() {
        Integer integer = albumService.selectTotalPage(2);
        System.out.println(integer);


    }

    @Test
    public void testTolcount() {
        Integer totalCount = albumService.selectTotalCount();
        System.out.println("totalCount = " + totalCount);
    }
}
