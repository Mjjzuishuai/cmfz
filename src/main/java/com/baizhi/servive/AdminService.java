package com.baizhi.servive;

import com.baizhi.entity.Admin;
import tk.mybatis.mapper.common.Mapper;

public interface AdminService{

    //后台：登陆查一个
    public String selectOne(Admin admin);
}
