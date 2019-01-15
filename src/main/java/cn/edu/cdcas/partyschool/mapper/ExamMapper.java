package cn.edu.cdcas.partyschool.mapper;

import cn.edu.cdcas.partyschool.model.Exam;

public interface ExamMapper {
    int deleteById(Integer id);

    int insert(Exam exam);

    int insertSelective(Exam exam);

    Exam queryById(Integer id);

    int updateByIdSelective(Exam exam);

    int updateById(Exam exam);
}