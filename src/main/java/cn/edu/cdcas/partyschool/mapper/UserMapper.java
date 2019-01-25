package cn.edu.cdcas.partyschool.mapper;

import cn.edu.cdcas.partyschool.model.User;

import java.util.List;

public interface UserMapper {
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
    int queryByStuendtNo(User user);

}