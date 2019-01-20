package cn.edu.cdcas.partyschool.util;

import java.util.Set;

;
/**
 * @Author Snail
 * @Description 单机版jedis工具接口
 * @CreateTime 2019/1/18
 */
public interface JedisClient {
    String set(String key,String value);
    String get(String key);
    Long del(String key);

    long hset(String hkey,String key,String value);
    String hget(String hkey,String key);
    Long hdel(String key,String field);

    /**
     * 将key的值递增1
     * @param key
     * @return
     */
    long incr(String key);
    /**
     * 设置key过期时间
     * @param key
     * @param second
     * @return
     */
    Long expire(String key,int second);
    /**
     * 查看key过期时间
     * @param key
     * @return
     */
    Long ttl(String key);
    /**
     *@Describe: 模糊查找key
     *
     *@Author Snail
     *@Date 2019/1/20
     */
    Set keys(String pattern );
}
