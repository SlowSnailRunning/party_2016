package cn.edu.cdcas.partyschool.util;

import java.util.Set;

;
/**
 * @Author Snail
 * @Description 单机版jedis工具接口
 * @CreateTime 2019/1/18
 */
public interface JedisClient {
//    String set(String key,String value);
//    String get(String key);
    Long del(String key);
//
    long hset(String hkey,String key,String value);
    String hget(String hkey,String key);
//    Long hdel(String key,String field);

//    Boolean exists(String key);
    Boolean hexists(String key,String field);


    /**
     * 将key的值递增1
     * @param key
     * @return
     */
//    long incr(String key);
    /**
     * 设置key过期时间
     * @param key
     * @param second
     * @return
     */
    Long expire(String key,int second);
    /**
     * 查看key过期时间
     * 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1,否则返回秒
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
//    Set keys(String pattern );

}
