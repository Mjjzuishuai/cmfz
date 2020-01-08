package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * (Mylog)实体类
 *
 * @author makejava
 * @since 2019-12-29 09:44:27
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Mylog implements Serializable {
    private static final long serialVersionUID = -81859473107293668L;
    @Id
    private String id;
    
    private String adminname;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date operatedate;
    
    private String thing;
    
    private String status;




}