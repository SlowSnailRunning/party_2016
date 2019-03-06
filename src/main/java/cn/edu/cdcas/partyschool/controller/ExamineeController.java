package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.UserSession;
import cn.edu.cdcas.partyschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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
    @RequestMapping("allQuestionInfoForStu")
    @ResponseBody
    public Map<String,Object> allQuestionInfoForStu(){


        return null;
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
