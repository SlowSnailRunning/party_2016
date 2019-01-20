package cn.edu.cdcas.partyschool.mapper;


import cn.edu.cdcas.partyschool.model.Question;

public interface QuestionMapper {
    int deleteById(Integer id);

    void clear();

    int insert(Question question);

    int insertSelective(Question question);

    Question queryById(Integer id);

    int updateByIdSelective(Question question);

    int updateById(Question question);
}