<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 01/11/2022
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form accept-charset="UTF-8" method="post" action="meals">
  <p>DateTime:
    <input type="text" name="dateTime" size="15" maxlength="30"/>
  </p>
  <p>Description
    <input type="text" name="description" size="30" maxlength="60"/>
  </p>
  <p>Calories
    <input type="text" name="calories" size="13" maxlength="10"/>
  </p>
  <input type="hidden" name="id" value="${param.id}">
  <input type="submit" name="update" value="Update">
</form>
</body>
</html>
