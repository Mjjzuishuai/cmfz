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
 * (Chapter)实体类
 *
 * @author makejava
 * @since 2019-12-28 15:10:43
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Chapter implements Serializable {
    private static final long serialVersionUID = -22885905959830365L;
    @Id
    private String id;
    
    private String title;
    
    private String url;
    
    private Double size;
    
    private String time;
    @Column(name = "create_time")
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @Column(name = "album_id")
    private String albumId;
}