package com.neil.servlet.util;

import com.neil.servlet.servlet.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "CheckUserLogin", urlPatterns = {"/CheckUserLogin"})
public class CheckUserLogin extends HttpServlet {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        conn = ConnectionManager.getConnection();
        HttpSession session = request.getSession();

        String sqlEmail = null;
        String sqlPassword = null;

        try {

            String userid = request.getParameter("acc");
            String pwd = request.getParameter("pwd");
            String checkbox = request.getParameter("remember_me");

            String sql = "SELECT * FROM `users_info` WHERE `email` =" +
                    "'" + userid.toString() + "'" + " and `password` = " + "'" + pwd.toString() + "'";
            System.out.println(sql);

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                sqlEmail = rs.getString("email");
                sqlPassword = rs.getString("password");
            }


            if (sqlEmail.equals(userid) && sqlPassword.equals(pwd)) {
                session.setAttribute("acc", sqlEmail);
                session.setAttribute("pwd", sqlPassword);

                if ("1".equals(checkbox)) {
                    Cookie accCookie = new Cookie("acc", sqlEmail);
                    Cookie pwdCookie = new Cookie("pwd", sqlPassword);

                    accCookie.setMaxAge(60 * 60 * 24);
                    pwdCookie.setMaxAge(60 * 60 * 24);

                    response.addCookie(accCookie);
                    response.addCookie(pwdCookie);
                }

                System.out.println("Welcome " + sqlEmail + "!");
                response.sendRedirect("wallPage.jsp");
            }

        } catch (Exception e) {

            System.out.println("Check Login username and password fail!" + "<br>");
            System.out.println(e.toString());

            response.sendRedirect("index.jsp?errMsg=0");
        }
    }
}
