<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users List</title>
</head>

<body>
<div>
    <%@include file="auth_header.jsp" %>
    <div>
        <div><span>List of Users </span></div>
        <table>
            <tr>
                <th>First name</th>
                <th>Last name</th>
                <th>Email</th>
                <th>SSO ID</th>
                <sec:authorize access="hasRole('ADMIN')">
                    <th width="100"></th>
                </sec:authorize>
                <sec:authorize access="hasRole('ADMIN')">
                    <th width="100"></th>
                </sec:authorize>

            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.ssoId}</td>
                    <sec:authorize access="hasRole('ADMIN')">
                        <td><a href="<c:url value='/editUser-${user.ssoId}' />">edit</a></td>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ADMIN')">
                        <td><a href="<c:url value='/deleteUser-${user.ssoId}' />">delete</a></td>
                    </sec:authorize>
                </tr>
            </c:forEach>
        </table>
    </div>
    <sec:authorize access="hasRole('ADMIN')">
        <div>
            <a href="<c:url value='/registerUser' />">Add New User</a>
        </div>
    </sec:authorize>
</div>
</body>
</html>