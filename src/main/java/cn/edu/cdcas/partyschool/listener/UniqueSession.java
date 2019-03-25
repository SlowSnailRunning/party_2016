package cn.edu.cdcas.partyschool.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * 完成单账号，单地点登录
 *
 * @author Snail
 */
public class UniqueSession implements HttpSessionAttributeListener {
    public static Map<String, HttpSession> sessionMap = new HashMap<>();

    /**
     * @Describe: 当向session中添加数据时触发
     * @Author Snail
     * @Date 2019/1/19
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String name = event.getName();
        if (name.equals("studentNo")) {
            HttpSession nowSession = event.getSession();
            String studentNo = (String) nowSession.getAttribute("studentNo");
// TODO: 2019/3/25 是否需要设置更新type ，同一个浏览器不能存在两个账号，混乱？？？
            HttpSession sessionInMap = sessionMap.get(studentNo == null ? 0 : studentNo);
            if (sessionInMap != null) {// TODO: 2019/3/25 排除管理员端的
                //复制sessionMap中的session到现在的session
                nowSession.setAttribute("examState", sessionInMap.getAttribute("examState"));
                nowSession.setAttribute("dan", sessionInMap.getAttribute("dan"));
                nowSession.setAttribute("duo", sessionInMap.getAttribute("duo"));
                nowSession.setAttribute("pan", sessionInMap.getAttribute("pan"));
                nowSession.setAttribute("tian", sessionInMap.getAttribute("tian"));
//				userSessionNow.setNumber("session覆盖成功");
                //移除之前的session
                sessionInMap.removeAttribute(studentNo);//removeAttribute
                sessionInMap.invalidate();//注销session
                sessionMap.remove(studentNo);

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
