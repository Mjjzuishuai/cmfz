package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * (Admin)实体类
 *
 * @author makejava
 * @since 2019-12-25 18:22:46
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Admin implements Serializable {
    private static final long serialVersionUID = 940510564229264729L;
    @Id
    private String id;
    
    private String username;
    
    private String password;
    private String salt;
    private List<Role> roles;
}