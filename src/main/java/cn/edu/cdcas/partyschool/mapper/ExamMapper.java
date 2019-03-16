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

    List<Exam> queryExamByName(@Param(value = "start")int start, @Param(value = "pageSize") int pageSize,@Param(value="examName") String examName) throws Exception;

    int updateByIdSelective(Exam exam) throws Exception;

    int updateTimeRangeById(Exam exam)throws Exception;

    String isCurrentExam() throws Exception;

    List<Exam> queryCurrentExamInformation() throws Exception;

    //查询指定时间段内是否有另一个考试（此版本考试系统一个时间段内仅仅允许存在一个考试）
    int queryAppointTimeQuantum(Exam exam) throws Exception;

    List<Exam> queryAllExamByKeyName(@Param("start") int start , @Param("pageSize") int pageSize , @Param("field") String field , @Param("value") String value);

    int queryExamNumsByField(@Param("field") String field, @Param("value") String value);

    Exam findExamById(String id) throws Exception;

    //查询初/补考是否超时
    Integer isOverTime(@Param("student_no")String student_no,@Param("exam_time")long exam_time) throws Exception;

    //更新考试结束时间
    Integer updateEndTime(@Param("id") Integer id);
}