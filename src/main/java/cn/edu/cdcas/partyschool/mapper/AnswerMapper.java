package cn.edu.cdcas.partyschool.mapper;

import cn.edu.cdcas.partyschool.model.Answer;

public interface AnswerMapper {
    int deleteById(Integer id);

    int insert(Answer answer);

    int insertSelective(Answer answer);

    Answer queryById(Integer id);

    int updateByIdSelective(Answer answer);

    int updateById(Answer answer);
}