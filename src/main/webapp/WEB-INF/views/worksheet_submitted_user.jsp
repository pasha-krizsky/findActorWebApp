<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Worksheet submitted</title>
</head>

<body>

<div>
    <div>
        <h1>Worksheet submitted!</h1>
        <a href="<c:url value='/worksheetsUser' />">View your submission</a>
    </div>
</div>

</body>
</html>