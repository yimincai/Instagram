package com.neil.servlet.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter", urlPatterns = {"/protected/*"})
public class EncodingFilter implements Filter {

    public void destroy() {
        System.out.println("filter destroy");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;chartset=utf-8");
        System.out.println("do filter");
        chain.doFilter(req, resp);
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        httpResponse.sendRedirect("../index.jsp?errMsg=1");
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("filter init");
    }

}
