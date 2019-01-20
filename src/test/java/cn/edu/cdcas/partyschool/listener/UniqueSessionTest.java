package cn.edu.cdcas.partyschool.listener;

import cn.edu.cdcas.partyschool.model.UserSession;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UniqueSessionTest {
    @Test
    public void test(){
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("test","testvalue1");
        System.out.println(jedis.get("test"));
        jedis.close();
        jedisPool.close();
    }
    @Test
    public void te(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        JedisPool jedisPool=(JedisPool) applicationContext.getBean("jedisSingle");
        Jedis jedis=jedisPool.getResource();
        ArrayList<UserSession> sessionArrayList = new ArrayList<>();

        UserSession userSession = new UserSession();
        userSession.setName("name1111");
        sessionArrayList.add(userSession);
        UserSession userSession1 = new UserSession();
        userSession1.setName("name2222");
        sessionArrayList.add(userSession1);


//        jedis.hset("keyy","fielddd",);
        System.out.println(jedis.get("test"));

        jedis.close();
        jedisPool.close();
    }
}