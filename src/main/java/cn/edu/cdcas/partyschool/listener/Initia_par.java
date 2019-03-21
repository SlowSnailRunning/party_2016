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
//        servletContext.setAttribute("php_Address","https://my.cdcas.edu.cn/core/default.php?from=iframe");
//        servletContext.setAttribute("php_login","https://my.cdcas.edu.cn");
        servletContext.setAttribute("php_Address", "https://172.20.253.19/core/default.php?from=iframe");
        servletContext.setAttribute("php_login", "https://172.20.253.19");
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
