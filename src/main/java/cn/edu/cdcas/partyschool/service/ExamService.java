package cn.edu.cdcas.partyschool.service;

import cn.edu.cdcas.partyschool.model.Exam;

import java.util.Map;

public interface ExamService {

    void clear() throws Exception;
    int queryAllExamRows() throws Exception;
    Map<String, Object> queryAllExamList(int start, int pageSize);
    Map<String,Object> queryAllExamByKeyName(int start,int pageSize,String field, String value);
    Map<String,Object> queryCurrentExamInformation();
    int queryExamNumsByField(String field, String value);
    String isCurrentExam() throws Exception;
    int insertSelective(Exam exam) throws Exception;
    int deleteById(Integer id) throws Exception;
    int updateByIdSelective(Exam exam) throws Exception;
    int queryAppointTimeQuantum(Exam exam) throws Exception;
    Map<String, Object> queryExamByName(int start,int pageSize,String examName) throws Exception;
//    int updateTimeRangeById(Exam exam)throws Exception;

//    boolean endNowExam()throws Exception;

    Integer updateEndTime(Integer id) throws Exception;

    Integer updateStartTime(Integer id) throws Exception;

//    Integer updateEndTime( Integer id)throws Exception;

    Exam findExamById(Integer id) throws Exception;
}
