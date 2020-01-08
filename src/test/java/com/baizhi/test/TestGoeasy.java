package com.baizhi.test;

import com.baizhi.Cmfz;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cmfz.class)
public class TestGoeasy {
    @Test
    public void name() {
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-198a2526cea5481f81c9f8fcdffaafb1");
        goEasy.publish("cmfz", "Hello, GoEasy!");
    }
}
