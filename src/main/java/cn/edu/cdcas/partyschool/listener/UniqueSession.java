package cn.edu.cdcas.partyschool.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.sound.midi.Soundbank;

import cn.edu.cdcas.partyschool.model.UserSession;
import cn.edu.cdcas.partyschool.util.impl.JedisClientSingle;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 完成单账号，单地点登录
 * @author Snail
 *
 */
public class UniqueSession implements HttpSessionAttributeListener{
	public static Map<String, HttpSession> sessionMap = new HashMap<>();

	/**
	 *@Describe: 当向session中添加数据时触发
	 *@Author Snail
	 *@Date 2019/1/19
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		String name = event.getName();
		if (name.equals("studentNo")) {
			HttpSession nowSession = event.getSession();
			String studentNo= (String) nowSession.getAttribute("studentNo");

			HttpSession sessionInMap = sessionMap.get(studentNo==null?0:studentNo);
			if (sessionInMap != null) {
				//复制sessionMap中的session到现在的session
				nowSession.setAttribute("examState",sessionInMap.getAttribute("examState"));
				nowSession.setAttribute("dan",sessionInMap.getAttribute("dan"));
				nowSession.setAttribute("duo",sessionInMap.getAttribute("duo"));
				nowSession.setAttribute("pan",sessionInMap.getAttribute("pan"));
				nowSession.setAttribute("tian",sessionInMap.getAttribute("tian"));
//				userSessionNow.setNumber("session覆盖成功");

				//移除之前的session
				sessionInMap.removeAttribute(studentNo);//removeAttribute
				sessionInMap.invalidate();//注销session

			}
			sessionMap.put(studentNo, event.getSession());
//			System.out.println("创建当前登录的session");
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {

	}

}
