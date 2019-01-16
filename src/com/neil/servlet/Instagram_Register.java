package com.neil.servlet;

import com.neil.util.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Instagram_Register", urlPatterns = {"/Instagram_Register"})
public class Instagram_Register extends HttpServlet {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        String sql = "insert into `users_info` values (?, ?, ?, ?, ?, ?, ?, ?)";

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
            response.sendRedirect("Register.jsp?userInfoError=0");
        } else {

            try {

                conn = ConnectionManager.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, null);
                pstmt.setString(2, firstName);
                pstmt.setString(3, lastName);
                pstmt.setString(4, email);
                pstmt.setString(5, password);
                pstmt.setString(6, phoneNumber);
                pstmt.setString(7, "0");
                pstmt.setString(8, null);

                int i = pstmt.executeUpdate();

                if (i > 0) {
                    out.println("Register successfully" + "<br>");
                    out.println("Please check your email to active the account ...");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
