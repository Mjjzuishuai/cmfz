package com.baizhi.servive;

import com.baizhi.entity.User;
import com.baizhi.entity.UserLocation;
import org.apache.ibatis.annotations.Param;

import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2020-01-02 22:25:07
 */
public interface UserService {

    //查所有
    public List<User> selectAll();
    //分页查所有
    public List<User> selectByPage(Integer page,Integer pagesize);
    //根据id查一个
    public User selectOne(String id);
    //根据id批量删除
    public void deleteByIds(String[] ids);
    //添加一个(注册)
    public void insert(User user);
    //修改一个
    public Map ipDate(User user);
    //查一共多少页
    public Integer selectTotalCount();
    //查一共多少行
    public Integer totalTotalPage(Integer pagesize);
    //查询某个时间段注册的人数
    public Integer selectByRegisterTime(String sex, Integer day);


    //前台:根据手机号和密码查一个（登陆）
    public Map selectByName(String phone, String password);
    //查询每个地区的男女人数
    public List<UserLocation> selectByLocation(String sex);
    //前台:发送验证码
    public Map sendCode(String phone);
    //前台:验证短信验证码
    public Map checkCode(String phone,String code);
    //前台:用户注册
    public Map register(User user);
    //前台:金刚道友随即展示五条数据
    public List<User> queryByRand(String id);
}