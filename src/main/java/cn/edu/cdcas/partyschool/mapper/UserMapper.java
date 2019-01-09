package cn.edu.cdcas.partyschool.mapper;

import cn.edu.cdcas.partyschool.model.User;

public interface UserMapper {
    int deleteById(Integer id);

    int insert(User user);

    int insertSelective(User user);

    User queryById(Integer id);

    int updateByIdSelective(User user);

    int updateById(User user);
}