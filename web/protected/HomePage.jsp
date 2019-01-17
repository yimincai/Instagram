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
<%
    int id = 0;
    String content = null;
    String sqlGetID = "SELECT max(post_id) from `post`";
    Connection conn = ConnectionManager.getConnection();
    PreparedStatement pstmt = conn.prepareStatement(sqlGetID);
    ResultSet rs = pstmt.executeQuery();

    try {

        while (rs.next()) {
            id = Integer.parseInt(rs.getString("max(post_id)"));
        }
        pstmt.close();
        rs.close();
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
    for (int i = id; i > 0; i--) {
        out.print("    <div align=\"center\">\n" +
                "        <img src=\"../images/");
        out.print(i);
        out.print(".jpg\" height=\"500\"><br><br>\n" +
                "        <form action=\"../InsertComments\" method=\"POST\">\n" +
                "            <input type=\"text\" name=\"comment\" size=\"70\">\n" +
                "            <input type=\"submit\" value=\"comment\"><br>\n<input type=\"hidden\" name=\"id\" value=\"");
        out.print(i);
        out.print("\"><br>" +
                "            <p>${username}:aaa</p>\n" +
                "        </form>\n" +
                "    </div>");
    }
%>
</body>
</html>


