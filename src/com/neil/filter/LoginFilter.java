package com.neil.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    public void destroy() {
        System.out.println("LoginFilter destroy.");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        System.out.println("do LoginFilter");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        String username = String.valueOf(session.getAttribute("username"));

        if (username != "null") {
            System.out.println(username + " pass the LoginFilter");
            chain.doFilter(req, resp);
        } else {
            System.out.println("You should not pass the LoginFilter");
            HttpServletResponse response = (HttpServletResponse) resp;
            response.sendRedirect("../index.jsp?errMsg=1");
        }
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("LoginFilter init.");
    }

}
