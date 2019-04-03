package com.wt.springboot.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @author Administrator
 * @date 2019-03-21 下午 12:18
 * PROJECT_NAME sand-demo
 */
public class MyRequestWrapper extends HttpServletRequestWrapper {

    private final Set<String> names = new HashSet<>();
    private final Map<String, String> headers = new HashMap<>();

    public MyRequestWrapper(HttpServletRequest request) {
        super(request);
        Enumeration<String> e = super.getHeaderNames();
        while (e.hasMoreElements()) {
            names.add(e.nextElement());
        }
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
        names.add(name);
    }
    @Override
    public String getHeader(String name) {
        if (headers.containsKey(name)) {
            return headers.get(name);
        } else {
            return super.getHeader(name);
        }
    }
    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(names);
    }
}
