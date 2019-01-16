<%--
  Created by IntelliJ IDEA.
  User: bravc
  Date: 2019/1/16
  Time: 下午 03:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout</title>
</head>
<body>

</body>
</html>

<%
    try {
        out.println("You are successfully logged out!<br>");
        out.println("Redirecting..");
        session.removeAttribute("username");
        session.removeAttribute("password");
        session.invalidate();
        response.sendRedirect("index.jsp?errMsg=2");
    } catch (Exception e) {
        out.println(e.toString());
    }
%>
