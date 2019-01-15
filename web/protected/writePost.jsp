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
    <title>Write a post</title>
</head>
<body>
<form method="post" action="Upload" enctype="multipart/form-data">
    Select file to upload :
    <input type="file" name="dataFile" id="fileChooser"/><br><br>
    <input type="submit" value="Upload"/>
</form>
</body>
</html>