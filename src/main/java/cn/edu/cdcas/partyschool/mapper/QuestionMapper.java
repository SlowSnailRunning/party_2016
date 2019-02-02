package cn.edu.cdcas.partyschool.mapper;


import cn.edu.cdcas.partyschool.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper {
/*    int insert(Question question);

    int updateByIdSelective(Question question);

    int updateById(Question question);*/

    int insertSelective(Question question);

    Question queryById(Integer id);

    List<Question> selectQueList(@Param(value = "start")int start,@Param(value = "pageSize") int pageSize,
                                 @Param(value = "intro") String intro,@Param(value = "type") String type) throws Exception;

    int countQue(@Param("intro") String intro,@Param("type") String type) throws Exception;

    void clear() throws Exception;

    int deleteById(Integer id) throws Exception;

    int updateState(@Param("id") Integer id,@Param("state") String state) throws Exception;

    int findQuestionIdMin();

    int findQuestionIdMax();
}