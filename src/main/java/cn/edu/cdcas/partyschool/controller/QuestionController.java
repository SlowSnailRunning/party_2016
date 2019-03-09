package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.Question;
import cn.edu.cdcas.partyschool.service.QuestionService;
import cn.edu.cdcas.partyschool.util.ExcelUtil;
import cn.edu.cdcas.partyschool.util.JSONResult;
import com.sun.org.glassfish.gmbal.ParameterNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    private QuestionService questionService;
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
    @RequestMapping("/selectQue")
    public Map<String, Object> showAllStuInfo(@RequestParam(required = false,defaultValue = "1") int page, @RequestParam(required = false,defaultValue = "20") int limit,
                                              @RequestParam(required = false,defaultValue="") String intro,@RequestParam(required = false,defaultValue="")String type) {
        Map<String,Object> map = null;
        try {
            //page：防止错误的page参数
            map = this.questionService.selectQue(page-1<0?0:page-1,limit,intro.trim(),type.trim());
        } catch (Exception e) {
            e.printStackTrace();
            //到异常页面，通知管理员

        }
        return map;
    }
    /**
     *@Describe: 清空题库
     *
     *@Author Snail
     *@Date 2019/1/26
     */
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    private boolean clear() {
        try {
            questionService.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     *@Describe: 删除选中
     *前端传入的数组在后台接收时，需要注意使用RequestParam
     *@Author Snail
     *@Date 2019/1/26
     */
    @RequestMapping(value = "/delete-multiple",method=RequestMethod.POST)
    private boolean deleteMultipleQue(@RequestParam("queId[]") int[] queId){
        try {
            questionService.deleteById(queId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     *@Describe:
     *
     *@Author Snail
     *@Date 2019/1/27
     */
    @RequestMapping(value = "/modifyState",method = RequestMethod.POST)
    private boolean modifyState(String state,int id){
        try {
            int rowsAffected = questionService.updateState(id,state);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

    }
}
