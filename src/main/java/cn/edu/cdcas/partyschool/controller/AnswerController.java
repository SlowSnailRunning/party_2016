package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.Answer;
import cn.edu.cdcas.partyschool.model.Question;
import cn.edu.cdcas.partyschool.service.AnswerService;
import cn.edu.cdcas.partyschool.util.JSONTableResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Resource
    private AnswerService answerService;


    @RequestMapping("/displayError")
    public JSONTableResult displayWrongAnswer(@RequestParam(value = "stuNo", required = false) String stuNo,
                                              @RequestParam(value = "page", required = false) int page, @RequestParam(value = "limit", required = false) int limit) {
        List<Answer> answers = answerService.queryAnswerByStuNo(stuNo, (page - 1) * limit, limit);
        List<Map<String, Object>> data = new ArrayList<>();
        answers.forEach(answer -> {
            Question question = answer.getQuestion();
            Map<String, Object> map = new HashMap<>();
            String intro = question.getIntro(), content = "", type = "";
            int question_type = question.getType();
            switch (question_type) {
                case 1:
                case 2:
                    content = "A." + question.getOptionA() + "  B." + question.getOptionB() +
                            "  C." + question.getOptionC() + "  D." + question.getOptionD();
                    break;
                case 3:
                case 4:
                case 5:
                    content = question.getOptionA();
                    break;
            }
            switch (question_type) {
                case 1:
                    type = "单选题";
                    break;
                case 2:
                    type = "多选题";
                    break;
                case 3:
                    type = "判断题";
                    break;
                case 4:
                    type = "填空题";
                    break;
                case 5:
                    type = "解答题";
                    break;
            }
            String result = answer.getResult();
            String s_answer = answer.getAnswer();

            map.put("type", type);
            map.put("questionId", answer.getQuestionId());
            map.put("intro", intro);
            map.put("options_content", content);
            map.put("result", result);
            map.put("answer", s_answer);
            data.add(map);
        });

        return new JSONTableResult(0, "success", data.size(), 200, data);
    }

}
