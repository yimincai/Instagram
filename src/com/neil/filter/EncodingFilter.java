package com.neil.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    public void destroy() {
        System.out.println("EncodingFilter destroy");
    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;chartset=utf-8");
        System.out.println("do EncodingFilter");
        chain.doFilter(req, resp);
    }
    public void init(FilterConfig config) throws ServletException {
        System.out.println("EncodingFilter init");
    }

}
