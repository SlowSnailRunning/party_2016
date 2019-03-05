package cn.edu.cdcas.partyschool.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/examinee")
public class StudentExamController {
    @RequestMapping("allQuestionInfoForStu")
    @ResponseBody
    public Map<String,Object> allQuestionInfoForStu(){


        return null;
    }
}
