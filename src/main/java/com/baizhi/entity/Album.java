package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * (Album)实体类
 *
 * @author makejava
 * @since 2019-12-27 15:41:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Album implements Serializable {
    private static final long serialVersionUID = -61302527275418544L;
    @Id
    private String id;
    
    private String title;
    
    private String score;
    
    private String author;
    
    private String broadcast;
    
    private Integer count;
    
    private String des;
    
    private String status;
    @Column(name = "create_date")
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private String cover;
}