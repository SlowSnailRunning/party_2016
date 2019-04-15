package cn.edu.cdcas.partyschool.aspect;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *
 * 定义动态数据源，实现通过集成Spring提供的AbstractRoutingDataSource，
 * 只需要实现determineCurrentLookupKey方法即可
 * 由于DynamicDataSource是单例的，线程不安全的，所以采用ThreadLocal保证线程安全,由DynamicDataSourceHolder完成。
 *
 *determineCurrentLookupKey是在service开始前执行的，通过它返回的值，寻找xml中对应的数据库；故数据源选择的aop应该在其之前执行完毕
 */
public class DynamicDataSource extends AbstractRoutingDataSource
{
    private static Logger log = Logger.getLogger(DynamicDataSource.class);
    @Override
    protected Object determineCurrentLookupKey()
    {
        // 使用DynamicDataSourceHolder保证线程安全，并且得到当前线程中的数据源key
        String dataSourceKey = DynamicDataSourceHolder.getDataSourceKey();
        //todo 打印选择的数据
        log.info("------------------------------get到的数据源dataSourceKey:"+dataSourceKey);
        return dataSourceKey;
    }
}