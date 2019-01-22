package cn.edu.cdcas.partyschool.service;

import cn.edu.cdcas.partyschool.util.impl.StatisticsPage;

public interface StatisticsInterface {
    public StatisticsPage statisticsCorrect(int pageSize,int currentPage);
}
