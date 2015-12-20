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
    Вы передали в качестве параметра: <B><%= request.getParameter("param") %></B>
    ${user}
    <h1>Топ 100 исполнителей:</h1>
    <table>
        <c:forEach var="position" items="${positions}">
            <tr>
                <td>${position.title}</td>
                <td>${position.count}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
