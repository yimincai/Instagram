<%--
  Created by IntelliJ IDEA.
  User: bravc
  Date: 2019/1/16
  Time: 下午 01:15
  To change this template use File | Settings | File Templates.
// TODO:display the pic, post content and comments in a profile.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.neil.util.ConnectionManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%
    String posterContent = null;
    String posterEmail = null;
    HttpSession session1 = (HttpSession) request.getSession();
    String profileUser = String.valueOf(session1.getAttribute("id"));

    int maxId = 0;
    String content = null;
    String sqlGetID = "SELECT * FROM `post` WHERE post.poster = " + profileUser;
    Connection conn = ConnectionManager.getConnection();
    PreparedStatement pstmt = conn.prepareStatement(sqlGetID);
    ResultSet rs = pstmt.executeQuery();

    ArrayList profilePostList = new ArrayList();

    try {

        while (rs.next()) {
            maxId = Integer.parseInt(rs.getString("post_id"));
            profilePostList.add(maxId);
            System.out.println("==========MAXID===========:" + maxId);
        }
        rs.close();
        pstmt.close();
        conn.close();
    } catch (Exception e) {
        System.out.println(e.toString());
    }


%>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<h1 align="center">Profile</h1>
<ul>
    <li><a href="HomePage.jsp">Home</a></li>
    <li><a href="CheckIn.jsp">Check In</a></li>
    <li><a href="Profile.jsp">Profile</a></li>
    <li><a href="Logout.jsp">Logout</a></li>
</ul>
<%
    HttpSession session2 = request.getSession();
    String user = String.valueOf(session2.getAttribute("id"));

    String leave_userid = null;
    String message = null;
    HashMap userIDandMessage = new HashMap();

    for (int y = profilePostList.size(); y > 0; y--) {

        try {
            String sqlGetComments = "SELECT * FROM user, comments WHERE comments.leave_userid = user.id and comments.post_id = ?";
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sqlGetComments);
            pstmt.setString(1, String.valueOf(profilePostList.get(y - 1)));
            rs = pstmt.executeQuery();


            int count = 0;
            while (rs.next()) {
                count++;
                leave_userid = rs.getString("email");
                message = rs.getString("message");
                userIDandMessage.put(count, leave_userid + " : " + message);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        try {
            String sqlGetPosterContent = "SELECT * FROM `post`, user WHERE `post`.`poster`= user.id AND post.post_id = ?";
            conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlGetPosterContent);
            preparedStatement.setString(1, String.valueOf(profilePostList.get(y - 1)));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                posterContent = rs.getString("content");
                posterEmail = rs.getString("email");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        System.out.printf("222222222222" + profilePostList.get(y - 1));

        out.print("    <div align=\"center\">\n" +
                "        <img src=\"../images/" + profilePostList.get(y - 1));
        out.print(".jpg\" height=\"500\"><br><br>\n" +
                "        <form action=\"../InsertCommentsForProfile\" method=\"POST\">\n" +
                "            <input type=\"text\" name=\"comment\" size=\"70\">\n" +
                "            <input type=\"submit\" value=\"comment\"><br>\n<input type=\"hidden\" name=\"id\" value=\"" + profilePostList.get(y - 1));
        out.print("\"><br>" + "Poster: " + posterEmail + " : " + posterContent +
                "            <p>");
        for (Object key : userIDandMessage.keySet()) {
            out.print(userIDandMessage.get(key) + "<br>");
            System.out.println(userIDandMessage.get(key) + " : " + key);
        }
        out.print("</p>\n" +
                "        </form>\n" +
                "    </div>");
    }
%>
</body>
</html>
