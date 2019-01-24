package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.mapper.StatisticsMapper;
import cn.edu.cdcas.partyschool.model.Question;
import cn.edu.cdcas.partyschool.model.Statistics;
import cn.edu.cdcas.partyschool.service.QuestionService;
import cn.edu.cdcas.partyschool.service.StatisticsInterface;
import cn.edu.cdcas.partyschool.util.impl.StatisticsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description TODO
 * @Date 2019/1/22 11:29
 * @Created by YR
 */
@RestController
public class StatisticsController {
    @Autowired
   private StatisticsInterface statisticsInterface;
    @Autowired
    private QuestionService questionService;
    @RequestMapping("/statisticsCorrect")
    public StatisticsPage statisticsCorrect(@RequestParam(value ="limit", defaultValue ="5",required=false)int pageSize, @RequestParam(value = "page" ,defaultValue ="1",required=false)int currentPage)
    {
        StatisticsPage statisticsPage = statisticsInterface.statisticsCorrect(pageSize,currentPage-1);
        System.out.println(statisticsPage);
        return statisticsPage;
    }
    @RequestMapping("/getQuestion")
    public Question getQuestion(int question_id)
    {
        return questionService.queryById(question_id);
    }

}
