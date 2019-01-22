package cn.edu.cdcas.partyschool.service.impl;
import cn.edu.cdcas.partyschool.mapper.StatisticsMapper;
import cn.edu.cdcas.partyschool.model.Statistics;
import cn.edu.cdcas.partyschool.service.StatisticsInterface;
import cn.edu.cdcas.partyschool.util.impl.StatisticsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StatisticsServiceImpl implements StatisticsInterface {
    @Autowired
    private StatisticsMapper statisticsMapper;
    public List<Statistics> statisticsCorrect()
    {
        List<Statistics> statistics = statisticsMapper.statisticsCorrect();
        Statistics statistic=null;
        for (int i=0;i<statistics.size();i++)
        {
            statistic=statistics.get(i);
            int type=Integer.parseInt(statistic.getType());
            statistics.get(i).setType(StatisticsUtil.getTypeString(type));
        }
        return statistics;
    }
}
