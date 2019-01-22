package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.mapper.StatisticsMapper;
import cn.edu.cdcas.partyschool.model.Statistics;
import cn.edu.cdcas.partyschool.service.StatisticsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/statisticsCorrect")
    public List<Statistics> statisticsCorrect()
    {
        List<Statistics> statistics = statisticsInterface.statisticsCorrect();
        System.out.println(statistics);
        return statistics;
    }
}
