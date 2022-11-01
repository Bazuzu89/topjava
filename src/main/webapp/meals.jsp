<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 25/10/2022
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<style>
table, th, td {
border: 1px solid black;
border-collapse: collapse;
}
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>

<c:forEach items="${meals}" var="mealTo">
    <c:if test="${mealTo.excess eq true}" var="ifExcess" >
    <tr style="color: red">
        <td>${mealTo.id}</td>
        <td>${mealTo.dateTime}</td>
        <td>${mealTo.description}</td>
        <td>${mealTo.calories}</td>
        <td><a href="meals?action=update&id=${mealTo.id}">Update</a></td>
        <td><a href="meals?action=delete&id=${mealTo.id}">Delete</a></td>
    </tr>
    </c:if>
    <c:if test="${mealTo.excess eq false}" var="ifExcess" >
    <tr style="color:green">
        <td>${mealTo.id}</td>
        <td>${mealTo.dateTime}</td>
        <td>${mealTo.description}</td>
        <td>${mealTo.calories}</td>
        <td><a href="meals?action=update&id=${mealTo.id}">Update</a></td>
        <td><a href="meals?action=delete&id=${mealTo.id}">Delete</a></td>
    </tr>
    </c:if>
</c:forEach>
</table>
</body>
</html>