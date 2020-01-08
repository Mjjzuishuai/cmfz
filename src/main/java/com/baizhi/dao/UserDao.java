package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.UserLocation;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2020-01-02 22:25:07
 */
public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {
    //根据时间查男女每一日，一周，一月，一年的注册数量
    public Integer selectByRegisterTime(@Param("sex") String sex,@Param("day") Integer day);
    //查询每个地区的男女数量分布图
    public List<UserLocation> selectByLocation(String sex);
    //随机展示五条数据
    public List<User> queryByRand(String id);
}