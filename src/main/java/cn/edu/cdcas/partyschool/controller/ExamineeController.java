package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.Answer;
import cn.edu.cdcas.partyschool.service.UserService;
import cn.edu.cdcas.partyschool.util.JSONResult;
import cn.edu.cdcas.partyschool.util.JedisClient;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/examinee")
public class ExamineeController {

    @Autowired
    private UserService userServiceImpl;
    /**
     *@Describe: 考生点击开始考试/补考到考试页面，获取随机到的题目id写入session
     *@Author Snail
     *@Date 2019/3/5
     */
    @RequestMapping("/allQuestionInfoForStu")
    @ResponseBody
    public Map<String,Object> allQuestionInfoForStu(HttpSession httpSession){
        Map<String,Object> requiredQuestions=new HashMap<>();
        try {
            //更新exam_state
            int examState= (int) httpSession.getAttribute("examState");
            int nowExamState=userServiceImpl.changeExamState((String) httpSession.getAttribute("studentNo"),examState);
            httpSession.setAttribute("examState",nowExamState);
            //更新考试开始时间
            //封装需要的数据
            requiredQuestions=userServiceImpl.requiredQuestionAndOther(httpSession);

            return requiredQuestions;
        } catch (Exception e) {
            e.printStackTrace();
            requiredQuestions.put("status",500);
            return requiredQuestions;

        }
    }

    @RequestMapping("/examAndStuInfo")
    @ResponseBody
    public  Map<String, Object> studentExamInfo(HttpSession httpSession){
//         ((UserSession) httpSession.getAttribute("partySys_user")).getNumber();
        String  studentNo= (String) httpSession.getAttribute("studentNo");
        Map<String,Object> studentExamInfo=null;
        try {
            studentExamInfo = userServiceImpl.studentExamInfo(studentNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentExamInfo;
    }
    /////////////////////////////////////del????
    @RequestMapping("/test")
    @ResponseBody
    public String test()
    {
        return "asdfasf";
    }
    /**
     *@Describe: 根据题目id判断是更新还是插入
     *@Author Snail
     *@Date 2019/3/9
     */
    @RequestMapping("/updateByQueId")
    public JSONResult updateByQueId(int id,String answer,HttpSession httpSession){

        try {
            boolean b =userServiceImpl.saveAnswer(id, answer,String.valueOf(httpSession.getAttribute("studentNo")),String.valueOf(httpSession.getAttribute("examState")));
            return new JSONResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult();
        }
    }
}
