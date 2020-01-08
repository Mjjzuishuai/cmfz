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

/**
 * (Article)实体类
 *
 * @author makejava
 * @since 2019-12-30 16:42:40
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Article implements Serializable {
    private static final long serialVersionUID = 441447954170039240L;
    @Id
    private String id;
    
    private String title;
    
    private String img;//封面
    
    private String content;//内容
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date")
    private Object createDate;//创建时间
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "publish_date")
    private Object publishDate;//发布时间
    
    private String status;
    @Column(name = "guru_id")
    private String guruId;//上师id

}