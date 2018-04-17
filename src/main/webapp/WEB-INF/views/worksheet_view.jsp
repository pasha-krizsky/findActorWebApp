<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Submit new worksheet</title>
</head>

<body>

<div>
    <%@include file="auth_header.jsp" %>
    <div>View worksheet</div>
    <table>
        <tr>
            <td>Name</td>
            <td>${worksheet.user.firstName}</td>
        </tr>
        <tr>
            <td>Surname</td>
            <td>${worksheet.user.lastName}</td>
        </tr>
        <tr>
            <td>Age</td>
            <td>${worksheet.age}</td>
        </tr>
        <tr>
            <td>Height</td>
            <td>${worksheet.height}</td>
        </tr>
        <tr>
            <td>Weight</td>
            <td>${worksheet.weight}</td>
        </tr>
        <tr>
            <td>Sex</td>
            <td>${worksheet.sex}</td>
        </tr>
        <tr>
            <td>Nationality</td>
            <td>${worksheet.nationality}</td>
        </tr>
        <tr>
            <td>Eye color</td>
            <td>${worksheet.eyeColor}</td>
        </tr>
        <tr>
            <td>Skin color</td>
            <td>${worksheet.skinColor}</td>
        </tr>
        <tr>
            <td>Hair color</td>
            <td>${worksheet.hairColor}</td>
        </tr>
        <tr>
            <td>Experience</td>
            <td>${worksheet.experience}</td>
        </tr>
        <tr>
            <td>Reason</td>
            <td>${worksheet.reason}</td>
        </tr>

    </table>
    <sec:authorize access="hasRole('AGENT')">
        <div>
            <a href="<c:url value='/worksheetsAgent' />">Go back</a>
        </div>
    </sec:authorize>

    <sec:authorize access="hasRole('DIRECTOR')">
        <div>
            <a href="<c:url value='/worksheetsDirector' />">Go back</a>
        </div>
    </sec:authorize>
</div>

</body>
</html>