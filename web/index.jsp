<%--
  Created by IntelliJ IDEA.
  User: Neil
  Date: 2019/1/9
  Time: 下午 04:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login to Instagram</title>
</head>
<body>
<form name="loginForm" method="post" action="CheckUserLogin">
    <table width="40%" bgcolor="white" align="center">
        <tr>
            <td colspan="2" align="center"><b><h1>Login Page</h1></b></td>
        </tr>
        <tr>
            <td>Username/Email:</td>
            <td><input type="email" size="25" name="username"></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" size="25" name="password"></td>
        </tr>
        <tr>
            <td>Remember me</td>
            <td><input type="checkbox" value="1" name="remember_me"></td>
            <td><input type="submit" value="Login"></td>
        </tr>
        <tr>
            <td>Didn't have account?</td>
            <td><a href="register.jsp">Register here!</a></td>
        </tr>
    </table>

</form>
</body>
</html>

<%
    Object reCode = request.getParameter("errMsg");

    if (String.valueOf(reCode).equals("null")) {
        out.println("");
    }

    if (String.valueOf(reCode).equals("0")) {
        out.println("<span style=\"font-weight: bold\"><p style=\"color: red\">There is something went wrong, plz login again.</p></span>");
    }

    if (String.valueOf(reCode).equals("1")) {
        out.println("<span style=\"font-weight: bold\"><p style=\"color: red\">Please try it again after login.</p></span>");
    }
%>
