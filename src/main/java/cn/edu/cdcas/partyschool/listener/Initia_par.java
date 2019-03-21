package cn.edu.cdcas.partyschool.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Description TODO
 * @Date 2019/3/9 16:02
 * @Created by YR
 */
public class Initia_par implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        //正式
//        servletContext.setAttribute("php_Address","https://my.cdcas.edu.cn/core/default.php?from=iframe");
//        servletContext.setAttribute("php_login","https://my.cdcas.edu.cn");
        //19
        servletContext.setAttribute("php_Address", "http://172.20.253.19/core/default.php?from=iframe");
        servletContext.setAttribute("php_login", "http://172.20.253.19");
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
