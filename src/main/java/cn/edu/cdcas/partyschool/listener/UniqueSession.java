package cn.edu.cdcas.partyschool.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import cn.edu.cdcas.partyschool.model.UserSession;

/**
 * 完成单账号，单地点登录
 * @author Snail
 *
 */
public class UniqueSession implements HttpSessionAttributeListener{
	private Map<String, HttpSession> map = new HashMap<String, HttpSession>();

	/**
	 * 当向session中添加数据时触发
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		String name = event.getName();

		if (name.equals("partySys_user")) {
			String userNumber = ((UserSession) event.getValue()).getNumber();
			if (map.get(userNumber) != null) {
				HttpSession session = map.get(userNumber);
				session.removeAttribute(userNumber);
				session.invalidate();
				//???？？？？能否在此处加入一些信息，带到前端页面去提示 用户被挤下线
				System.out.println("移除之前登录的当前账号！!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
			}
			map.put(userNumber, event.getSession());
			System.out.println("创建当前登录的session");
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
