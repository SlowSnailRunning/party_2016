package cn.edu.cdcas.partyschool.mapper;

import cn.edu.cdcas.partyschool.model.Exam;

public interface ExamMapper {
    int deleteById(Integer id) throws Exception;

    int insert(Exam exam);

    int insertSelective(Exam exam) throws Exception;

    Exam queryById(Integer id) throws Exception;

    int updateByIdSelective(Exam exam) throws Exception;

    int updateById(Exam exam);

    int selectState() throws Exception;
}