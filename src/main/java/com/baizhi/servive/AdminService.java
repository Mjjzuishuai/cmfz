package com.baizhi.servive;

import com.baizhi.entity.Admin;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AdminService{

    //后台：登陆查一个
    public String selectOne(Admin admin);
    //茶所有
    public List<Admin> selectAll();
    //增
    public void add(Admin admin);
    //删
    public void delete(String id);
    //改
    public void upDate(Admin admin);
}
