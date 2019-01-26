package cn.edu.cdcas.partyschool.service;

import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.util.JSONResult;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    int deleteById(Integer id);

    int deleteByStuNo(String stuNo);

    void clear();

    int insert(User user);

    int insertSelective(User user);

    User queryById(Integer id);

    User queryByStuNo(String stuNo);

    List<User> queryAll();

    List<User> queryAllByPaging(int offsetSize, int pageSize);

    int updateByIdSelective(User user);

    int updateByStuNoSelective(User user);

    int updateByStuNo(User user);

    int queryStuNums();

    boolean isEmpty();    //judge whether the numbers of student is empty.


    int insertManger(User user);

    boolean existsManager(User user);

    JSONResult MangerAuthorityControl(HttpSession httpSession);

    boolean exists(User user);
}
