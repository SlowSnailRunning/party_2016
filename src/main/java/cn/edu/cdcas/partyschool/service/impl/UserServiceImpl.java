package cn.edu.cdcas.partyschool.service.impl;
import cn.edu.cdcas.partyschool.mapper.ExamMapper;
import cn.edu.cdcas.partyschool.mapper.UserMapper;
import cn.edu.cdcas.partyschool.model.Manger;
import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.service.UserService;
import cn.edu.cdcas.partyschool.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private ExamMapper examMapper;


    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public int deleteByStuNo(String stuNo) {
        return userMapper.deleteByStuNo(stuNo);
    }

    @Override
    public void clear() {
        userMapper.clear();
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int insertSelective(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.queryById(id);
    }

    @Override
    public User queryByStuNo(String stuNo) {
        return userMapper.queryByStuNo(stuNo);
    }

    @Override
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public List<User> queryAllByPaging(int offsetSize, int pageSize) {
        return userMapper.queryAllByPaging(offsetSize, pageSize);
    }

    @Override
    public List<User> queryAllByPagingAndKey(int offsetSize, int pageSize, String field, String value) {
        return userMapper.queryAllByPagingAndKey(offsetSize, pageSize, field, value);
    }

    @Override
    public int updateByIdSelective(User user) {
        return userMapper.updateByIdSelective(user);
    }

    @Override
    public int updateByStuNoSelective(User user) {
        return userMapper.updateByStuNoSelective(user);
    }

    @Override
    public int updateByStuNo(User user) {
        return userMapper.updateByStuNo(user);
    }

    @Override
    public int queryStuNums() {
        return userMapper.queryStuNums();
    }

    @Override
    public int queryStuNumsByField(String field, String value) {
        return userMapper.queryStuNumsByField(field, value);
    }

    @Override
    public boolean isEmpty() {
        return userMapper.queryStuNums() == 0;
    }

    @Override
    /*需在登陆时session中设置httpSession.setAttribute("authority")*/
    public JSONResult MangerAuthorityControl(HttpSession httpSession) {
        httpSession.setAttribute("authority", "ROOT");
        if ("ROOT" == httpSession.getAttribute("authority")) {//是超级管理员，给管理员管理权限
            return new JSONResult(0, "", 0);
        } else {
            return new JSONResult(1, "", 0);
        }
    }

    @Override
    public int insertManger(User user) {
        return userMapper.insert(user);
    }

    @Override
    public boolean existsManager(User user) {
        return userMapper.queryManagerNums(user) > 0;
    }

    @Override
    public boolean exists(User user) {
        return userMapper.queryByStuNo(user.getStudentNo()) != null;
    }

    @Override
    public Map<String, Object> queryMangerMap(int page, int limit) {
        Map<String, Object> map = new HashMap<>();
        List<Manger> list = userMapper.queryMangerList((page - 1) * limit, limit);
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", userMapper.queryMangerCount());
        map.put("status", 200);
        map.put("data", list);
        return map;
    }

    public Map<String, Object> dimQueryMangerByName(String name) {
        Map<String, Object> map = new HashMap<>();
        List<Manger> list = userMapper.dimQueryMangerByName(name);
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", list.size());//直接给list大小
        map.put("status", 200);
        map.put("data", list);
        return map;
    }

    @Override
    public String findType(String number) throws Exception {

        return userMapper.findType(number);
}
    /**
     *@Describe: 根据学号判断是否有自己的考试
     *@Author Snail
     *@Date 2019/2/1
     */
    @Override
    public String determineExam(String number) throws Exception {
        String exam_state=userMapper.isHaveExamByStudentNo(number);

        if("0".equals(exam_state)) {
            return "未考";
        }else if("3".equals(exam_state)){
            return "未补考";
        }else {
            return "无考试";
        }
    }
}
