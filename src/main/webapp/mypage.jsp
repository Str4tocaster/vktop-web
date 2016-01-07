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
    <%--отображаем кнопку авторизации если в cookie нет id пользователя--%>
    <c:if test="${empty user}">
        <%@ include file="login.jsp" %>
    </c:if>

    ${user}

    <form action="main" method="post">
        <input type="text" name="specialId" value="${specialId}" title="id пользователя"/>
        <p>
            <select name="topType">
                <option disabled>Выберите тип рейтинга</option>
                <option value=1 ${topType == 1 ? 'selected' : ''} >По трекам</option>
                <option value=2 ${topType == 2 ? 'selected' : ''} >По исполнителям</option>
            </select>
            <select name="topSize">
                <option disabled>Выберите размер рейтинга</option>
                <option value=10 ${topSize == 10 ? 'selected' : ''}>10</option>
                <option value=50 ${topSize == 50 ? 'selected' : ''}>50</option>
                <option value=100 ${topSize == 100 ? 'selected' : ''}>100</option>
            </select>
            <input type="date" name="topDate" value="${topDate}">
        </p>
        <p>
            <select name="topSex">
                <option disabled>Выберите пол</option>
                <option value=0 ${topSex == 0 ? 'selected' : ''}>Любой</option>
                <option value=1 ${topSex == 1 ? 'selected' : ''}>Только среди девушек</option>
                <option value=2 ${topSex == 2 ? 'selected' : ''}>Только среди парней</option>
            </select>
            <input type="text" name="topAge" value="${topAge}" title="возраст"/>
            <input type="submit"/>
        </p>
    </form>
    <h1>Топ 100 исполнителей:</h1>

    ${output}

    <%--<table>--%>
        <%--<c:forEach var="position" items="${positions}">--%>
            <%--<tr>--%>
                <%--<td>${position.title}</td>--%>
                <%--<td>${position.count}</td>--%>
            <%--</tr>--%>
        <%--</c:forEach>--%>
    <%--</table>--%>
</body>
</html>
