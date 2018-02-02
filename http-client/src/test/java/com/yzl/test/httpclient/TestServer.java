package com.yzl.test.httpclient;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Before;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServer {

    static Server server = new Server(8080);

    /**
     * 启动一个jetty server
     *
     * @throws Exception
     */
    public static void start() throws Exception {
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(DefaultServlet.class, "/");
        ServletHolder servletHolder = new ServletHolder(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resp.getWriter().append("This is test. ").append(String.valueOf(System.currentTimeMillis())).flush();
            }
        });
        servletContextHandler.addServlet(servletHolder, "/test");
        server.start();
    }

    public static void stop() throws Exception {
        server.stop();
    }
}
