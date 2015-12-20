<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: valer
  Date: 02.12.2015
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>VK-Top</title>
</head>
<body>
    <form action="login" method="post">
        <input type="submit" name="login" value="login" />
    </form>
    <span class="success">${message}</span>
</body>
</html>
