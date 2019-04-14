package cn.edu.cdcas.partyschool.util.impl;

import cn.edu.cdcas.partyschool.util.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientSingle implements JedisClient {
    @Autowired
    private JedisPool jedisPool;

//    @Autowired
//    private AppletContext appletContext;

    /*    @Override
        public String set(String key, String value) {
            Jedis jedis = jedisPool.getResource();
            String string = jedis.set(key, value);
            jedis.close();
            return string;
        }



        @Override
        public long incr(String key) {
            Jedis jedis = jedisPool.getResource();
            Long result = jedis.incr(key);
            jedis.close();
            return result;
        }

        @Override
        public Set keys(String pattern) {
            Jedis jedis = jedisPool.getResource();
            Set<String> keys = jedis.keys(pattern);
            return keys;
        }



        @Override
        public Long hdel(String key, String field) {
            Jedis jedis = jedisPool.getResource();
            Long result = jedis.hdel(key, field);
            jedis.close();
            return result;
        }



        @Override
        public Boolean exists(String key) {
            Jedis jedis=jedisPool.getResource();
            Boolean exists=jedis.exists(key);
            jedis.close();
            return exists;
        }
    */
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.get(key);
        jedis.close();
        return string;
    }

    @Override
    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    @Override
    public Boolean hexists(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        Boolean exists = jedis.hexists(key, field);
        jedis.close();
        return exists;
    }

    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(hkey, key, value);
        jedis.close();
        return result;
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.hget(hkey, key);
        jedis.close();
        return string;
    }

    @Override
    public Long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key, second);
        jedis.close();
        return result;
    }
}
