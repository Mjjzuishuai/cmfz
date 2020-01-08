package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * (Guru)实体类
 *
 * @author makejava
 * @since 2019-12-30 19:11:32
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Guru implements Serializable {
    private static final long serialVersionUID = 787496726575810085L;
    @Id
    private String id;
    
    private String name;
    
    private String photo;
    
    private String status;
    @Column(name = "nick_name")
    private String nickName;
}