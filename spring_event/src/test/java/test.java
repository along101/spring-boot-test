import org.apache.catalina.core.StandardWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;

import javax.servlet.*;
import javax.swing.*;
import java.lang.reflect.Proxy;

/**
 * Created by zhongyi on 2017/12/4.
 */
public class test {
    public static void main(String[] args){
        ApplicationContext applicationContext;
        ConfigurableApplicationContext configurableApplicationContext;
        AbstractApplicationContext abstractApplicationContext;
        ApplicationListener applicationListener;
        ApplicationEventMulticaster applicationEventMulticaster;
        ApplicationContextEvent applicationContextEvent;
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster;
        JComponent jComponent;
        Proxy proxy;
        Servlet servlet;
        ServletConfig servletConfig;
        ServletContext servletContext;
        ServletRequest servletRequest;
        ServletResponse servletResponse;
        StandardWrapper standardWrapper;
    }

}
