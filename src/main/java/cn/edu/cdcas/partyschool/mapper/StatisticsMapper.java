package cn.edu.cdcas.partyschool.mapper;

import cn.edu.cdcas.partyschool.model.Statistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Date 2019/1/22 9:26
 * @Created by YR
 */
public interface StatisticsMapper {
    public List<Statistics> statisticsCorrect(@Param(value ="pageSize") int pageSize,@Param(value ="currentPage") int currentPage);
    public int statisticsTotal();
}
