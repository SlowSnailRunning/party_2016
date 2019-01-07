package cn.edu.cdcas.partyschool.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import cn.edu.cdcas.partyschool.model.User;

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

		if (name.equals("usercode")) {
			User user = (User) event.getValue();
			if (map.get(user.getName()) != null) {
				HttpSession session = map.get(user.getName());
				session.removeAttribute(user.getName());
				session.invalidate();
				System.out.println("移除之前登录的当前账号！!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
			}
			map.put(user.getName(), event.getSession());
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
