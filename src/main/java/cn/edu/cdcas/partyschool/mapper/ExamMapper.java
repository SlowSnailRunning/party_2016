package cn.edu.cdcas.partyschool.mapper;

import cn.edu.cdcas.partyschool.model.Exam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamMapper {

    int queryAllExamRows()throws Exception;

    void clear()throws Exception;

    int queryExamRowsByName(String examName)throws Exception;

    List<Exam> queryAllExamList(@Param(value = "start")int start, @Param(value = "pageSize") int pageSize)throws Exception;

    int deleteById(Integer id) throws Exception;

    int insert(Exam exam)throws Exception;

    int insertSelective(Exam exam) throws Exception;

    List<Exam> queryExamByName(@Param(value = "start")int start, @Param(value = "pageSize") int pageSize,String examName) throws Exception;

    int updateByIdSelective(Exam exam) throws Exception;

    int updateById(Exam exam)throws Exception;

    int selectState() throws Exception;
}