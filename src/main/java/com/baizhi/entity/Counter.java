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


@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Counter implements Serializable {
    private static final long serialVersionUID = 605425412637270625L;
    @Id
    private String id;
    
    private String title;
    
    private Integer count;
    @Column(name = "create_date")
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Object createDate;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "course_id")
    private String courseId;
}