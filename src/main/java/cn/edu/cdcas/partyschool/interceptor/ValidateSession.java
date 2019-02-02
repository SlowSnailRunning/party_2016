package cn.edu.cdcas.partyschool.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.cdcas.partyschool.util.impl.JedisClientSingle;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.cdcas.partyschool.model.UserSession;

import java.util.Iterator;
import java.util.Set;

/**
 *@Describe: 对于非公开url请求，校验session是否存在,是否唯一
 *
 *@Author Snail
 *@Date 2019/1/20
 */
public class ValidateSession implements HandlerInterceptor{
	//公开地址
	private static final String[] IGNORE_URI = {"/login"};

	@Autowired
	private JedisClientSingle jedisClientSingle;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("into ValidateSession");
		UserSession user =  (UserSession) (request.getSession()).getAttribute("partySys_user");
        String url = request.getRequestURI();
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                return true;
            }
        }
        if (user == null) {
            response.sendRedirect(request.getContextPath()+"/jsp/test.jsp");
            return false;
        }
        /*无法实现对某一个session的销毁，某一个字符串session反序列化为对象
        else {
			Set keys = jedisClientSingle.keys("spring:session:sessions:????????????????????????????????????");
			System.out.println("keys");
            for (Iterator key = keys.iterator(); key.hasNext(); ) {
//                key:spring:session:sessions:637e65d2-4eda-4f2d-aee4-30d93bbfcf85
//�� sr *cn.edu.cdcas.partyschool.model.UserSessioni�os:�~� L namet Ljava/lang/String;L numberq ~ L typeq ~ xpt usernaemt 21111111112t 	user type
                String sessionStr = jedisClientSingle.hget((String) key.next(), "sessionAttr:partySys_user");
                System.out.println(sessionStr);//
                UserSession userSession=JSON.parseObject(sessionStr,UserSession.class);
            }
//			jedisClientSingle.hget(spring:session:sessions:2b254525-e9c6-4341-8435-5031624f3312 sessionAttr:partySys_user);
		}*/

        return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
