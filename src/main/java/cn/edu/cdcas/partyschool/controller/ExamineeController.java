package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.UserSession;
import cn.edu.cdcas.partyschool.service.UserService;
import cn.edu.cdcas.partyschool.util.JSONResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/examinee")
public class ExamineeController {

    @Autowired
    private UserService userServiceImpl;
    /**
     *@Describe: 考生点击开始考试/补考，获取随机到的题目id写入session
     *@Author Snail
     *@Date 2019/3/5
     */
    @RequestMapping("/allQuestionInfoForStu")
    @ResponseBody
    public List<Map<String,Object>> allQuestionInfoForStu(HttpSession httpSession){
        List<Map<String,Object>> requiredQuestions=new ArrayList<>();
        try {
            //更新exam_state
            UserSession sys_user = (UserSession) httpSession.getAttribute("partySys_user");
            int examState=sys_user.getStudentExamState();
            int nowExamState=userServiceImpl.changeExamState((String) httpSession.getAttribute("studentNo"),examState);
            sys_user.setStudentExamState(nowExamState);

            requiredQuestions=userServiceImpl.requiredQuestionAndOther(httpSession);

            return requiredQuestions;
        } catch (Exception e) {
            e.printStackTrace();
            return requiredQuestions;
        }
    }

    @RequestMapping("/examAndStuInfo")
    @ResponseBody
    public Map<String, Object> studentExamInfo(HttpSession httpSession){

        String  studentNo= ((UserSession) httpSession.getAttribute("partySys_user")).getNumber();
        Map<String,Object> studentExamInfo=null;
        try {
            studentExamInfo = userServiceImpl.studentExamInfo(studentNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentExamInfo;
    }
    @RequestMapping("/test")
    @ResponseBody
    public String test()
    {
        return "asdfasf";
    }
}
