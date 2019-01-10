package cn.edu.cdcas.partyschool.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.cdcas.partyschool.model.UserSession;

/**
 * 对于非公开url请求，校验session是否存在
 * @author Snail
 *
 */
public class ValidateSession implements HandlerInterceptor{
	//公开地址
	private static final String[] IGNORE_URI = {"/login"};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
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
