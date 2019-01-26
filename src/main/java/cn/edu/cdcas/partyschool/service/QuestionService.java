package cn.edu.cdcas.partyschool.service;


import cn.edu.cdcas.partyschool.model.Question;

import java.util.Map;

public interface QuestionService {
    int deleteById(Integer id);

    void clear();

    int insert(Question record);

    int insertSelective(Question record);

    Question queryById(Integer id);

    int updateByIdSelective(Question record);

    int updateById(Question record);

    Map<String, Object> selectQue(int currentPage, int pageSize, String intro, String type) throws Exception;

    int queryQueNums(String intro, String type) throws Exception;
}