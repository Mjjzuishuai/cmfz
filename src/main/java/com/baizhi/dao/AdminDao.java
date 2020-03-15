package com.baizhi.dao;

import com.baizhi.entity.Admin;
import tk.mybatis.mapper.common.Mapper;

public interface AdminDao extends Mapper<Admin> {
    //查询每个管理员的权限信息
    public Admin selectAdminInfo(String principal);
}
