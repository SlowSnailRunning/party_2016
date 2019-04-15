package cn.edu.cdcas.partyschool.aspect;

import org.apache.log4j.Logger;

/**
 *使用ThreadLocal技术来记录当前线程中的数据源的key
 */
public class DynamicDataSourceHolder
{
    private static Logger log = Logger.getLogger(DynamicDataSource.class);

    // 写库对应的数据源key
    private static final String MASTER = "master";
    // 读库对应的数据源key
    private static final String SLAVE = "slave";

    // 使用ThreadLocal保护该线程私有的变量,记录当前线程的数据源key
    /*使用ThreadLocal的场景最好满足两个条件，一是该对象不需要在多线程之间共享；二是该对象需要在线程内被传递
    * */
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    /**
     * 设置数据源key
     */
    public static void setDataSourceKey(String key)
    {
        holder.set(key);
    }
    /**
     * 获取数据源key
     */
    public static String getDataSourceKey()
    {
        String s = holder.get();
        /*if (s == null) {
            s =MASTER;// 默认主库
        }*/
        return s;
    }
    public static void clearDataSource()
    {
        log.info("移除clearDataSource");
        holder.remove();
    }
    /**
     * 标记写库
     */
    public static void markAsMaster()
    {
        setDataSourceKey(MASTER);
    }

    /**
     * 标记读库
     */
    public static void markAsSlave()
    {
        setDataSourceKey(SLAVE);
    }


}