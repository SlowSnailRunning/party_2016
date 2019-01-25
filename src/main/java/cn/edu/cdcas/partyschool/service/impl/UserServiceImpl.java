package cn.edu.cdcas.partyschool.service.impl;

import cn.edu.cdcas.partyschool.mapper.UserMapper;
import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.service.UserService;
import cn.edu.cdcas.partyschool.util.JSONResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public int deleteByStuNo(String stuNo) {
        return userMapper.deleteByStuNo(stuNo);
    }

    @Override
    public void clear() {
        userMapper.clear();
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int insertSelective(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.queryById(id);
    }

    @Override
    public User queryByStuNo(String stuNo) {
        return userMapper.queryByStuNo(stuNo);
    }

    @Override
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public int updateByIdSelective(User user) {
        return userMapper.updateByIdSelective(user);
    }

    @Override
    public int updateByStuNoSelective(User user) {
        return userMapper.updateByStuNoSelective(user);
    }

    @Override
    public int updateByStuNo(User user) {
        return userMapper.updateByStuNo(user);
    }

    @Override
    public int queryStuNums() {
        return userMapper.queryStuNums();
    }

    @Override
    public boolean isEmpty() {
        return userMapper.queryStuNums() == 0;
    }
    @Override
    public JSONResult addManger(User user) {
        try {
            //先查询
            int flag = userMapper.queryByStuendtNo(user);
            if (flag != 0)//存在
            {
                return new JSONResult(1, "用户账号已存在！！", 200);
            } else {//不存在
                userMapper.insert(user);
                return new JSONResult(0, "添加成功！！", 200);
            }
        } catch (Exception e) {//异常
            e.printStackTrace();
            return new JSONResult(3, "数据库异常！！，联系管理员", 200);
        }
    }
    public boolean exists(User user) {
        return userMapper.queryByStuNo(user.getStudentNo()) != null;
    }
}
