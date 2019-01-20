package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.Question;
import cn.edu.cdcas.partyschool.service.QuestionService;
import cn.edu.cdcas.partyschool.util.ExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * The question controller related with operations of question,
 * such as uploading and downloading excel file and so on.
 *
 * @author Char Jin
 * @date 2019-01-20
 */


@Controller
@RequestMapping("/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @ResponseBody
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") CommonsMultipartFile file) {
        ExcelUtil excelUtil = new ExcelUtil();
        Map<Integer, List<String>> map = null;
        Question question = new Question();
        try {
            map = excelUtil.getDataMap(file, 7);
            map.forEach((key, value) -> {
                question.setIntro(value.get(0));
                question.setOptionA(value.get(1));
                question.setOptionB(value.get(2));
                question.setOptionC(value.get(3));
                question.setOptionD(value.get(4));
                question.setResult(value.get(5));
                question.setType(Integer.valueOf(value.get(6)));
            });
            questionService.insertSelective(question);
        } catch (IOException e) {
            return "上传错误!";
        }
        return "上传成功!";
    }

    @ResponseBody
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    private String clear() {
        questionService.clear();
        return "清空完成";
    }

}
