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
	private Map<String, HttpSession> map = new HashMap<String, HttpSession>();
	@Autowired
	private JedisClientSingle jedisClientSingle;

	/**
	 *@Describe: 当向session中添加数据时触发  			spring:session:sessions:????????????????????????????????????
	 * 从所有session中拿到学号，如果redis的session中的一个学号与当前session学号相同，则删除redis中的session，重新添加
	 *@Author Snail
	 *@Date 2019/1/19
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
/*		String name = event.getName();
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
		}*/
		System.out.println("dfasf");
		Set keys = jedisClientSingle.keys("spring:session:sessions:????????????????????????????????????");
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
