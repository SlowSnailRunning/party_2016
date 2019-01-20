package cn.edu.cdcas.partyschool.service;

import cn.edu.cdcas.partyschool.model.User;

public interface UserService {
    int deleteById(Integer id);

    void clear();

    int insert(User user);

    int insertSelective(User user);

    User queryById(Integer id);

    int updateByIdSelective(User user);

    int updateById(User user);

}
