package com.yzl.cros;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author yutu
 * @date 2018/12/28
 */
@Component
@WebFilter(urlPatterns = {"/**"}, filterName = "SomeFilter")
public class SomeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String cookies = null;
        if (req.getCookies() != null) {
            cookies = Arrays.stream(req.getCookies())
                    .map(cookie -> cookie.getName() + "=" + cookie.getValue()).collect(Collectors.joining(","));

        }
        System.out.println(req.getMethod() + " : " + cookies);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
