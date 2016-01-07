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
    <style type="text/css">
        .myform {
            margin-left: 30%; /* Отступ слева */
            width: 40%; /* Ширина слоя */
            background: #fc0; /* Цвет фона */
            padding: 10px; /* Поля вокруг текста */
            font: 12pt sans-serif;
        }
        .myform2 {
            margin-left: 30%; /* Отступ слева */
            width: 40%; /* Ширина слоя */
            background: #FFd; /* Цвет фона */
            padding: 10px; /* Поля вокруг текста */
            font: 12pt sans-serif;
        }
    </style>
</head>
<body>
    <%--отображаем кнопку авторизации если в cookie нет id пользователя--%>
    <c:if test="${empty user}">
        <%@ include file="login.jsp" %>
    </c:if>

    <div class="myform">
        <table width="100%">
            <tr>
                <td align="center"><h1>Топ аудиозаписей ВКонтакте</h1></td>
            </tr>
        </table>
        <form action="main" method="post">
            <table width="100%" cellspacing="0" cellpadding="4">
                <tr>
                    <td>${user}</td>
                    <td><input type="text" name="specialId" value="${specialId}" title="id пользователя"/></td>
                </tr>
                <tr>
                    <td>Тип рейтинга</td>
                    <td>Размер рейтинга</td>
                    <td>Дата</td>
                </tr>
                <tr>
                    <td>
                        <select name="topType">
                            <option disabled>Выберите тип рейтинга</option>
                            <option value=1 ${topType == 1 ? 'selected' : ''} >По трекам</option>
                            <option value=2 ${topType == 2 ? 'selected' : ''} >По исполнителям</option>
                        </select>
                    </td>
                    <td>
                        <select name="topSize">
                            <option disabled>Выберите размер рейтинга</option>
                            <option value=10 ${topSize == 10 ? 'selected' : ''}>10</option>
                            <option value=50 ${topSize == 50 ? 'selected' : ''}>50</option>
                            <option value=100 ${topSize == 100 ? 'selected' : ''}>100</option>
                        </select>
                    </td>
                    <td><input type="date" name="topDate" value="${topDate}"></td>
                </tr>
                <tr>
                    <td>Пол</td>
                    <td>Возраст</td>
                </tr>
                <tr>
                    <td>
                        <select name="topSex">
                            <option disabled>Выберите пол</option>
                            <option value=0 ${topSex == 0 ? 'selected' : ''}>Любой</option>
                            <option value=1 ${topSex == 1 ? 'selected' : ''}>Только среди девушек</option>
                            <option value=2 ${topSex == 2 ? 'selected' : ''}>Только среди парней</option>
                        </select>
                    </td>
                    <td><input type="text" name="topAge" value="${topAge}" title="возраст"/></td>
                    <td><input type="submit" value="составить топ"/></td>
                </tr>
            </table>
        </form>
    </div>

    <div class="myform2">
        ${output}

        <table width="100%">
            <c:forEach var="position" items="${positions}">
                <tr>
                    <td align="center">${position.id}</td>
                    <td align="left">${position.title}</td>
                    <td align="center">${position.count}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
