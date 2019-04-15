package cn.edu.cdcas.partyschool.aspect;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 定义数据源的AOP切面，通过service中方法名判断是应该走读库还是写库，优先级最高
 */
public class DataSourceAspect
{
    private static final Logger log = Logger.getLogger(DataSourceAspect.class);

    private List<String> slaveMethodPattern = new ArrayList<>();

//    private static final String[] defaultSlaveMethodStart = new String[] { "quer", "find", "get" };

    private String[] slaveMethodStart;

    /**
     * 读取事务管理中的策略
     * @param txAdvice
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void setTxAdvice(TransactionInterceptor txAdvice) throws Exception
    {
        if (txAdvice == null)
        {
            log.info("没有配置事务管理策略");
            return;
        }
        // 从txAdvice获取到策略配置信息
        TransactionAttributeSource transactionAttributeSource = txAdvice.getTransactionAttributeSource();
        if (!(transactionAttributeSource instanceof NameMatchTransactionAttributeSource))
        {
            return;
        }
        // 使用反射技术获取到NameMatchTransactionAttributeSource对象中的nameMap属性值
        NameMatchTransactionAttributeSource matchTransactionAttributeSource = (NameMatchTransactionAttributeSource) transactionAttributeSource;
        Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");

        log.info("nameMapField AAAAA:" + nameMapField);

        nameMapField.setAccessible(true); // 设置该字段可访问
        // 获取nameMap的值
        Map<String, TransactionAttribute> map = (Map<String, TransactionAttribute>) nameMapField
                .get(matchTransactionAttributeSource);
        // 遍历nameMap
        for (Map.Entry<String, TransactionAttribute> entry : map.entrySet())
        {
            log.info("entity结果:" + entry.toString());
            // 判断之后定义了ReadOnly的策略才加入到slaveMethodPattern
            if (!entry.getValue().isReadOnly())
            {
                continue;
            }
            slaveMethodPattern.add(entry.getKey());
        }
    }

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
        log.info("方法名称：" + methodName);
        boolean isSlave = false;
        if (slaveMethodPattern.isEmpty())
        {
            log.info("当前Spring容器中没有配置事务策略，采用方法名匹配方式");
            isSlave = isSlave(methodName);
        }
        else
        {
            log.info("使用策略规则匹配");
            for (String mappedName : slaveMethodPattern)
            {
                if (isMatch(methodName, mappedName))
                {
                    isSlave = true;
                    break;
                }
            }
        }

        if (isSlave)
        {
            log.info("标记为读库");
            DynamicDataSourceHolder.markAsSlave();
        }
        else
        {
            log.info("标记为写库");
            DynamicDataSourceHolder.markAsMaster();
        }
    }

    /**
     * 非常重要的一个方法，在方法正常执行完成后，立即将ThreadLocal中存储的数据源标签清除，保证ThreadLocal用完即删
     */
    public void afterReturning() {
        DynamicDataSourceHolder.clearDataSource();
    }

    /**
     * xml中DIslave的方法名前缀
     * @param slaveMethodStart
     */
    public void setSlaveMethodStart(String[] slaveMethodStart)
    {
        this.slaveMethodStart = slaveMethodStart;
    }
//    public void after(JoinPoint point)
//    {
//        DynamicDataSourceHolder.clearDataSource();
//    }

    /**
     * 判断是否为读库
     */
    private Boolean isSlave(String methodName)
    {
        log.info("根据Dao方法名前缀判断是否走从库.");
        boolean b = StringUtils.startsWithAny(methodName, slaveMethodStart);
        return b;
    }

    /**
     * 通配符匹配
     *
     * Return if the given method name matches the mapped name.
     * <p>
     * The default implementation checks for "xxx*", "*xxx" and "*xxx*" matches,
     * as well as direct equality. Can be overridden in subclasses.
     *
     * @param methodName
     *            the method name of the class
     * @param mappedName
     *            the name in the descriptor
     * @return if the names match
     * @see PatternMatchUtils#simpleMatch(String,
     *      String)
     */
    protected boolean isMatch(String methodName, String mappedName)
    {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }
    /**
     * 获取
     * @return
     */
//    public String[] getSlaveMethodStart()
//    {
////        if (this.slaveMethodStart == null)
////        {
////            log.info("没有指定slaveMethodStart ，使用默认");
////            // 没有指定，使用默认
////            return defaultSlaveMethodStart;
////        }
//        return slaveMethodStart;
//    }

}