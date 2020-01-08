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
 * (Course)实体类
 *
 * @author makejava
 * @since 2020-01-06 20:23:19
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Course implements Serializable {
    private static final long serialVersionUID = 948508805873281424L;
    @Id
    private String id;
    
    private String title;
    @Column(name = "user_id")
    private String userId;
    
    private String type;
    @Column(name = "create_date")
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
}