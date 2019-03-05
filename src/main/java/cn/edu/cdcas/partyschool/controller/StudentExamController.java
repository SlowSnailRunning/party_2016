package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.UserSession;
import cn.edu.cdcas.partyschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("/examinee")
public class StudentExamController {

    @Autowired
    private UserService userServiceImpl;

    @RequestMapping("allQuestionInfoForStu")
    @ResponseBody
    public Map<String,Object> allQuestionInfoForStu(){
        return null;
    }

    @RequestMapping("/examInfo")
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
}
