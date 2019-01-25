package cn.edu.cdcas.partyschool.service;

import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.util.JSONResult;

import java.util.List;

public interface UserService {
    int deleteById(Integer id);

    int deleteByStuNo(String stuNo);

    void clear();

    int insert(User user);

    int insertSelective(User user);

    User queryById(Integer id);

    User queryByStuNo(String stuNo);

    List<User> queryAll();

    int updateByIdSelective(User user);

    int updateByStuNoSelective(User user);

    int updateByStuNo(User user);

    int queryStuNums();

    boolean isEmpty();    //judge whether the numbers of student is empty.
    JSONResult addManger(User user);

    boolean exists(User user);
}
