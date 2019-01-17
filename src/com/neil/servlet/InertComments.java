package com.neil.servlet;

import com.neil.util.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "InertComments", urlPatterns = {"/InsertComments"})
public class InertComments extends HttpServlet {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //start to insert content to DB
        HttpSession session = request.getSession();
        String leave_user = String.valueOf(session.getAttribute("id"));
        String comment = request.getParameter("comment");
        String id = request.getParameter("id");
        String sql = "INSERT INTO `comments`(`comment_id`, `post_id`, `leave_userid`, `message`, `message_timestamp`) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, null);
            pstmt.setString(2, id);
            pstmt.setString(3, leave_user);
            pstmt.setString(4, comment);
            pstmt.setString(5, null);
            pstmt.executeUpdate();

            response.sendRedirect("protected/HomePage.jsp");

        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }
}
