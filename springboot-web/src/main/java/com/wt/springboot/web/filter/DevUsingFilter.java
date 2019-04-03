package com.wt.springboot.web.filter;

import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Administrator
 * @date 2019-03-21 下午 12:12
 * PROJECT_NAME sand-demo
 */
@WebFilter(
        filterName = "AFilter",
        urlPatterns = {"/*"}
)
public class DevUsingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MyRequestWrapper myRequestWrapper = new MyRequestWrapper((HttpServletRequest) servletRequest);
        if(ObjectUtils.isEmpty(myRequestWrapper.getHeader("Authorization"))){
            myRequestWrapper.addHeader("Authorization","gisinfoeyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiY3JlYXRlZCI6MTU1MzEzOTc2NDQ1NCwiZXhwIjoxNTUzMTQ5NzY0fQ.VDLoVR4Tv2A90WQvt5k47GSFAtzLq7FY89sTAtGwoSHrOQpWe_CBy3zKgRRXoQhIr9kXKnZQvbmU5mlyqDzc_w");
        }
        filterChain.doFilter(myRequestWrapper,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
