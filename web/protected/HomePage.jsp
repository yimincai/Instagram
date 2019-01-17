<%--
  Created by IntelliJ IDEA.
  User: bravc
  Date: 2019/1/15
  Time: 上午 02:41
  To change this template use File | Settings | File Templates.
// TODO 1:display the pic, post content and comments.
// TODO 2:impl the comments system.
// TODO 3:impl the Liked system.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.neil.util.ConnectionManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.HashMap" %>
<%
    int maxId = 0;
    String content = null;
    String sqlGetID = "SELECT max(post_id) from `post`";
    Connection conn = ConnectionManager.getConnection();
    PreparedStatement pstmt = conn.prepareStatement(sqlGetID);
    ResultSet rs = pstmt.executeQuery();

    try {

        while (rs.next()) {
            maxId = Integer.parseInt(rs.getString("max(post_id)"));
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
    <title>Wall Blocks</title>
</head>
<body>
<h1 align="center">Home</h1>
<ul>
    <li><a href="HomePage.jsp">Home</a></li>
    <li><a href="CheckIn.jsp">Check In</a></li>
    <li><a href="Profile.jsp">Profile</a></li>
    <li><a href="Logout.jsp">Logout</a></li>
</ul>
<%
    String leave_userid = null;
    String message = null;
    HashMap userIDandMessage = new HashMap();

    for (int i = maxId; i > 0; i--) {

        try {
            String sqlGetComments = "SELECT * FROM user, comments WHERE comments.leave_userid = user.id and comments.post_id = ?";
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sqlGetComments);
            pstmt.setString(1, String.valueOf(i));
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

        String posterContent = null;
        String posterEmail = null;

        try {
            String sqlGetPosterContent = "SELECT * FROM `post`, user WHERE `post`.`poster`= user.id AND post.post_id = ?";
            conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlGetPosterContent);
            preparedStatement.setString(1, String.valueOf(i));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                posterContent = rs.getString("content");
                posterEmail = rs.getString("email");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        out.print("    <div align=\"center\">\n" +
                "        <img src=\"../images/" + i);
        out.print(".jpg\" height=\"500\"><br><br>\n" +
                "        <form action=\"../InsertComments\" method=\"POST\">\n" +
                "            <input type=\"text\" name=\"comment\" size=\"70\">\n" +
                "            <input type=\"submit\" value=\"comment\"><br>\n<input type=\"hidden\" name=\"id\" value=\"" + i);
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


