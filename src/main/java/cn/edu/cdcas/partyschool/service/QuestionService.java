package cn.edu.cdcas.partyschool.service;


import cn.edu.cdcas.partyschool.model.Question;

import java.util.Map;

public interface QuestionService {
/*    int insert(Question record);
    int updateById(Question record);
    int updateByIdSelective(Question record);*/

    Question queryById(Integer id);

    int insertSelective(Question record);

    Map<String, Object> selectQue(int currentPage, int pageSize, String intro, String type) throws Exception;

    int queryQueNums(String intro, String type) throws Exception;

    void deleteById(int[] id) throws Exception;

    void clear() throws Exception;

    int updateState(Integer id,String state)throws Exception;

    /*Set<Integer> randomQuestionIdArray() throws Exception;*/
}