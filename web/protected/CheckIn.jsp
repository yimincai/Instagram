<%--
  Created by IntelliJ IDEA.
  User: Neil
  Date: 2019/1/11
  Time: 下午 08:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Check In</title>
</head>
<body>
<h1 align="center">Check In</h1>
<ul>
    <li><a href="HomePage.jsp">Home</a></li>
    <li><a href="CheckIn.jsp">Check In</a></li>
    <li><a href="Profile.jsp">Profile</a></li>
    <li><a href="Logout.jsp">Logout</a></li>
</ul>
<table align="center">
    <form method="post" action="Upload" enctype="multipart/form-data">
        <td>
            <p>Content: </p>
            <textarea name="content" rows="10" cols="100"></textarea><br>
            <p>Select file to upload :</p>
            <input type="file" name="dataFile" id="fileChooser"/><br><br>
            <input type="submit" value="Upload"/>
        </td>
    </form>
</table>
</body>
</html>