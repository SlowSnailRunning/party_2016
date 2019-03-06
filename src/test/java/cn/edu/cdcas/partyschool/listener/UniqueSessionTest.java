package cn.edu.cdcas.partyschool.listener;

import cn.edu.cdcas.partyschool.model.UserSession;
import cn.edu.cdcas.partyschool.util.impl.JedisClientSingle;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class UniqueSessionTest {
    /*@Test
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
        //
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        JedisPool jedisPool=(JedisPool) applicationContext.getBean("jedisSingle");
        Jedis jedis=jedisPool.getResource();

        UserSession userSession = new UserSession();
        //userSession.setName("name1111");
        userSession.setNumber("201617");

        String string = JSON.toJSONString(userSession);
        jedis.set("usersession",string);

        UserSession session = JSON.parseObject(jedis.get("usersession"), UserSession.class);
//        System.out.println(session.getName());


        System.out.println(jedis.get("test"));

        jedis.close();
        jedisPool.close();
    }

    @Test
    public void tesrsr(){
        Set<Long> dogSet =new HashSet<>();
            dogSet.add((long)1);
            dogSet.add((long)2);
            dogSet.add((long)6);

            System.out.println("We have " + dogSet.size() + " white dogs!");

            if(dogSet.contains((long)2)){
                System.out.println("We have a white dog!");
            }else{
                System.out.println("No white dog!");
            }
    }*/
}