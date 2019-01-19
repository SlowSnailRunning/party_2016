package cn.edu.cdcas.partyschool.mapper;


import cn.edu.cdcas.partyschool.model.Question;

public interface QuestionMapper {
    int deleteById(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question queryById(Integer id);

    int updateByIdSelective(Question record);

    int updateById(Question record);
}