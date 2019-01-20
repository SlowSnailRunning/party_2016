package cn.edu.cdcas.partyschool.service;


import cn.edu.cdcas.partyschool.model.Question;

public interface QuestionService {
    int deleteById(Integer id);

    void clear();

    int insert(Question record);

    int insertSelective(Question record);

    Question queryById(Integer id);

    int updateByIdSelective(Question record);

    int updateById(Question record);
}