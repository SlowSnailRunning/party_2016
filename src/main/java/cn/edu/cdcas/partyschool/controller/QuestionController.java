package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.Question;
import cn.edu.cdcas.partyschool.service.QuestionService;
import cn.edu.cdcas.partyschool.util.ExcelUtil;
import cn.edu.cdcas.partyschool.util.JSONResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @ResponseBody
    @RequestMapping("/upload")
    public JSONResult upload(@RequestParam("file") MultipartFile file) {
        ExcelUtil excelUtil = new ExcelUtil();
        Map<Integer, List<String>> map = null;
        Question question = new Question();
        try {
            map = excelUtil.getDataMap(file, 7);
            map.forEach((key, value) -> {
                if (key != -1) {
                    question.setIntro(value.get(0));
                    question.setOptionA(value.get(1));
                    question.setOptionB(value.get(2));
                    question.setOptionC(value.get(3));
                    question.setOptionD(value.get(4));
                    question.setResult(value.get(5));
                    question.setType(Integer.valueOf(value.get(6)));
                    questionService.insertSelective(question);
                }
            });
        } catch (IOException e) {
            return new JSONResult(1, e.getMessage(), 404);
        }
        return new JSONResult(0, "题库导入成功!", 200);
    }
    /**
     *@Describe: 查找题库，包括有/无条件
     *
     *@Author Snail
     *@Date 2019/1/26
     */
    @ResponseBody
    @RequestMapping("/selectQue")
    public Map<String, Object> showAllStuInfo(@RequestParam(required = false,defaultValue = "1") int page, @RequestParam(required = false,defaultValue = "20") int limit,
                                              @RequestParam(required = false,defaultValue="") String intro,@RequestParam(required = false,defaultValue="")String type) {
        //page：防止错误的page参数
        Map<String,Object> map = null;
        try {
            map = this.questionService.selectQue(page-1<0?0:page-1,limit,intro.trim(),type.trim());
        } catch (Exception e) {
            e.printStackTrace();
            //到异常页面，通知管理员

        }
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    private String clear() {
        questionService.clear();
        return "清空完成";
    }

}
