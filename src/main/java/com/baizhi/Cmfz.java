package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

/*springboot核心jar*/
@SpringBootApplication
@MapperScan("com.baizhi.dao")
public class Cmfz {
    public static void main(String[] args) {
      SpringApplication.run(Cmfz.class, args);
    }
}
