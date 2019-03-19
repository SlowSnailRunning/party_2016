package cn.edu.cdcas.partyschool.mapper;

import cn.edu.cdcas.partyschool.model.Answer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnswerMapper {
    int deleteById(Integer id);

    int insert(Answer answer);

    int insertSelective(Answer answer);

    Answer queryById(Integer id);

    int updateByIdSelective(Answer answer);

    int updateById(Answer answer);

    List<Answer> queryAnswerByStuNo(@Param("stuNo") String stuNo, @Param("offsetSize") int offsetSize, @Param("pageSize") int pageSize);
    void deleteAnswer(@Param("stu_no") String stu_no);
}