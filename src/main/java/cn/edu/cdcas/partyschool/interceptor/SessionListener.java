package cn.edu.cdcas.partyschool.interceptor;

import cn.edu.cdcas.partyschool.listener.UniqueSession;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;

public class SessionListener  implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
//        System.out.println("asdfasfasfsf");
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String studentNo = (String) session.getAttribute("studentNo");
        System.out.println(studentNo);
        Map<String, HttpSession> sessionMap = UniqueSession.sessionMap;
        sessionMap.remove(studentNo);
//        for(String key : sessionMap.keySet()){
//            System.out.println(key);
//        }
    }
}
