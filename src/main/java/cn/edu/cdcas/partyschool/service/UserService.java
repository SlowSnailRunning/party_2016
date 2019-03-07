package cn.edu.cdcas.partyschool.service;

import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.util.JSONResult;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {
    int deleteById(Integer id);

    int deleteByStuNo(String stuNo);

    void clear();

    int insert(User user);

    int insertSelective(User user);

    User queryById(Integer id);

    User queryByStuNo(String stuNo) throws Exception;

    List<User> queryAll();

    List<User> queryAllByPaging(int offsetSize, int pageSize);

    List<User> queryAllByPagingAndKey(int offsetSize, int pageSize, String field, String value);

    int updateByIdSelective(User user);

    int updateByStuNoSelective(User user);

    int updateByStuNo(User user);

    int queryStuNums();

    int queryStuNumsByField(String field, String value);

    boolean isEmpty();    //judge whether the numbers of student is empty.

    int insertManger(User user);

    boolean existsManager(User user);

    JSONResult MangerAuthorityControl(HttpSession httpSession);

    boolean exists(User user) throws Exception;

    Map<String, Object> queryMangerMap(int page, int limit);


    Map<String, Object> dimQueryMangerByName(String name);

    String findType(String number) throws Exception;

    String determineExam(String number) throws Exception;

    Map<String,Object> studentExamInfo(String studentNo) throws Exception;

    String isLoginSuccess(String token) throws Exception;

    List<Map<String, Object>> requiredQuestionAndOther(HttpSession httpSession) throws Exception;

    int changeExamState(int examState) throws  Exception;
}
