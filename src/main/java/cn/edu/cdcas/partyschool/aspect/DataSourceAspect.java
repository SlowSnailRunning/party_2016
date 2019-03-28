package cn.edu.cdcas.partyschool.aspect;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

/**
 * 定义数据源的AOP切面，通过该Service的方法名判断是应该走读库还是写库
 *
 * @author liudiwei 2018年3月12日
 *
 */
public class DataSourceAspect
{

    private static final Logger log = Logger.getLogger(DataSourceAspect.class);

    /**
     * 在进入Service方法之前执行
     *
     * @param point
     *            切面对象
     */
    public void before(JoinPoint point)
    {
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        if (isSlave(methodName))
        {
            // 标记为读库
            DynamicDataSourceHolder.markAsSlave();
        }
        else
        {
            // 标记为写库
            DynamicDataSourceHolder.markAsMaster();
        }
    }

    /**
     * 判断是否为读库
     *
     * @param methodName
     * @return
     *
     */
    private Boolean isSlave(String methodName)
    {
        log.info("根据Service方法名前缀判断是否走从库.");
        boolean b = StringUtils.startsWithAny(methodName, "query", "quer", "find", "get");
        return b;
    }
}