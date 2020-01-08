package com.baizhi.servive;

import com.baizhi.aspect.GoEasy;
import com.baizhi.entity.User;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.UserLocation;
import com.baizhi.servive.UserService;
import com.baizhi.util.Md5Utils;
import com.baizhi.util.SendSmsUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2020-01-02 22:25:07
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> selectAll() {
        List<User> users = userDao.selectAll();
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> selectByPage(Integer page, Integer pagesize) {
        List<User> users = userDao.selectByRowBounds(null, new RowBounds((page - 1) * pagesize, pagesize));
        return users;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public User selectOne(String id) {
        User user = userDao.selectByPrimaryKey(id);
        return user;
    }

    //根据手机号查一个(登陆)
    @Override
    public Map selectByName(String phone, String password) {
        HashMap map = new HashMap<>();
        String msg = null;
        try {
            //判断用户是否存在
            User user2 = new User();
            user2.setPhone(phone);
            User user1 = userDao.selectOne(user2);
            //判断密码是否存在
            String md5Code = Md5Utils.getMd5Code(password);
            String salt = user1.getSalt();
            String md5Password = md5Code+salt;
            if (user1 == null) {
                msg = "用户不存在";
                map.put("status", "-200");
                map.put("message", msg);
            } else if (!user1.getPassword().equals(md5Password)) {
                msg = "密码不正确";
                map.put("status", "-200");
                map.put("message", msg);
            } else {
                msg = "登录成功";
                map.put("status", "200");
                map.put("user", user1);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", msg);
            return map;
        }
    }

    //前台：发送短信验证码
    public Map sendCode(String phone) {
        //调用工具类获取四位数短信验证码
        String code = Md5Utils.getSalt(4);
        //拿redis标准操作set集合
        SetOperations setOperations = redisTemplate.opsForSet();
        //往redis里边存值(手机号,短信验证码)
        setOperations.add(phone, code);
        //设置一个有效期
        RedisOperations operations = setOperations.getOperations();
        operations.expire(phone, 60, TimeUnit.SECONDS);

        HashMap<Object, Object> map = new HashMap<>();
        try {
            SendSmsUtil.sendSms(phone, code);
            map.put("status", "200");
            map.put("msg", "发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "-200");
            map.put("msg", "发送失败");
        }
        return map;
    }


    @GoEasy(value = "")
    @Override
    public void deleteByIds(String[] ids) {
        userDao.deleteByIdList(Arrays.asList(ids));
    }

    @GoEasy(value = "")
    @Override
    public void insert(User user) {
        String password = user.getPassword();
        //调用工具类生成Md5密码
        String md5Code = Md5Utils.getMd5Code(password);
        //调用工具类生成随机的盐
        String salt = Md5Utils.getSalt(8);
        String newPassword = md5Code + salt;
        user.setPassword(newPassword);
        userDao.insert(user);
    }


    //前台:用户修改
    @GoEasy(value = "")
    @Override
    public Map ipDate(User user) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            String id = user.getId();
            userDao.updateByPrimaryKeySelective(user);
            User user1 = userDao.selectByPrimaryKey(id);
            map.put("status", "200");
            map.put("user", user1);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "200");
            return map;
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Integer selectTotalCount() {
        int i = userDao.selectCount(null);
        return i;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Integer totalTotalPage(Integer pagesize) {
        int i = userDao.selectCount(null);
        Integer totalPage = null;
        totalPage = i % pagesize == 0 ? i / pagesize : i / pagesize + 1;
        return totalPage;
    }

    //查询某个时间点上注册账号的用户
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Integer selectByRegisterTime(String sex, Integer day) {
        Integer integer = userDao.selectByRegisterTime(sex, day);
        return integer;
    }


    //查询每个地区男女的注册人数

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<UserLocation> selectByLocation(String sex) {
        List<UserLocation> list = userDao.selectByLocation(sex);
        return list;
    }


    //前台:校验短信验证码
    @Override
    public Map checkCode(String phone, String clientCode) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            //根据手机号从redis数据库里边拿短信验证码
            String redisCode = (String)redisTemplate.opsForSet().pop(phone);
            //用客户端的验证码和redis数据库中的验证码对比
            if (redisCode.equals(clientCode)) {
                map.put("status", "200");
            } else {
                map.put("status", "-200");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "-200");
            return map;
        }
    }

    //前台:用户注册
    @GoEasy(value = "")
    public Map register(User user) {
        HashMap<Object, Object> map = new HashMap<>();
        try {

            String id = user.getId();
            String md5Code = Md5Utils.getMd5Code(id);
            String salt = Md5Utils.getSalt(4);
            String newPassword = md5Code+salt;
            user.setPassword(newPassword);
            user.setSalt(salt);
            user.setId(UUID.randomUUID().toString());
            user.setRigestDate(new Date());
            userDao.insert(user);
            map.put("status","200");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("user", user);
            return map;
        }
    }

    //前台:金刚道友随即展示五条数据
    @Override
    public List<User> queryByRand(String id) {
        try {
            List<User> users = userDao.queryByRand(id);
            return users;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}