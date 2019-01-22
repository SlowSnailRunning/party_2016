package cn.edu.cdcas.partyschool.util.impl;
import cn.edu.cdcas.partyschool.model.Statistics;
import java.util.List;
/**
 * @Description TODO
 * @Date 2019/1/22 17:15
 * @Created by YR
 */
public class StatisticsPage {
    private int total;
   // private int pageSize;
   // private int currentPage;
    List<Statistics> Statistics;
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public List<cn.edu.cdcas.partyschool.model.Statistics> getStatistics() {
        return Statistics;
    }
    public void setStatistics(List<cn.edu.cdcas.partyschool.model.Statistics> statistics) {
        Statistics = statistics;
    }
}
