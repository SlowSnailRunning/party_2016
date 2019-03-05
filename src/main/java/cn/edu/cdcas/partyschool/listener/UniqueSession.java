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
		if (name.equals("partySys_user")) {
			String userNumber = ((UserSession) event.getValue()).getNumber();
			if (sessionMap.get(userNumber==null?0:userNumber) != null) {
				HttpSession session = sessionMap.get(userNumber);

				session.removeAttribute(userNumber);
				session.invalidate();
				//???？？？？能否在此处加入一些信息，带到前端页面去提示 用户被挤下线
//				System.out.println("移除之前登录的当前账号！!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
			}
			sessionMap.put(userNumber, event.getSession());
//			System.out.println("创建当前登录的session");
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub

	}

}
