package cn.edu.cdcas.partyschool.mapper;

import cn.edu.cdcas.partyschool.model.Manger;
import cn.edu.cdcas.partyschool.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteById(Integer id);

    int deleteByStuNo(String stuNo);

    void clear();

    int insert(User user);

    int insertSelective(User user);

    User queryById(Integer id);


    List<User> queryAll();

    List<User> queryAllByPaging(@Param("offsetSize") int offsetSize, @Param("pageSize") int pageSize);

    List<User> queryAllByPagingAndKey(@Param("offsetSize") int offsetSize, @Param("pageSize") int pageSize, @Param("field") String field, @Param("value") String value);

    int updateByIdSelective(User user);

    int updateByStuNoSelective(User user);

    int updateByStuNo(User user);

    int queryStuNums();

    int queryStuNumsByField(@Param("field") String field, @Param("value") String value);

    int queryManagerNums(User user);

    List<Manger> queryMangerList(@Param(value = "page") int page, @Param(value = "limit") int limit);

    List<Manger> dimQueryMangerByName(@Param(value = "name") String name);

    int queryMangerCount();//查看管理员数量

    /**
     *@Describe: 根据账号判断是否是管理员...
     *@Author Snail
     *@Date 2019/2/1
     */
    String findType(@Param("number") String number) throws Exception;
    /**
     *@Describe: 根据学号查找此时他是否存在考试
     *@Author Snail
     *@Date 2019/2/1
     */
    String isHaveExamByStudentNo(@Param("studentNo")String studentNo) throws Exception;

    User queryByStuNo(String stuNo) throws Exception;
}