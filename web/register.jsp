<%--
  Created by IntelliJ IDEA.
  User: bravc
  Date: 2019/1/14
  Time: 下午 09:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>

<h1 align="center">Instagram Register</h1>

<form action="Instagram_Register" method="post">
    <table style="width: 40%" align="center">
        <tr>
            <td>First Name:</td>
            <td><input type="text" name="first_name"></td>
        </tr>
        <tr>
            <td>Last Name:</td>
            <td><input type="text" name="last_name"/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type="email" name="email"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password"/></td>
        </tr>
        <tr>
            <td>Phone Number:</td>
            <td><input type="tel" name="phoneNumber"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Register"/></td>
        </tr>
    </table>
</form>

</body>
</html>
