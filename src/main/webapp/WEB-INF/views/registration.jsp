<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Registration</title>
</head>

<body>
<div>

    <div>
        <h4>Register new user</h4>
    </div>

    <form:form method="POST" modelAttribute="user">
        <form:input type="hidden" path="id" id="id"/>
        <div>
            <div>
                <label for="firstName">First Name</label>
                <div>
                    <form:input type="text" path="firstName" id="firstName"/>
                    <div>
                        <form:errors path="firstName"/>
                    </div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="lastName">Last Name</label>
                <div>
                    <form:input type="text" path="lastName" id="lastName"/>
                    <div>
                        <form:errors path="lastName"/>
                    </div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="ssoId">Login</label>
                <div>
                    <c:choose>
                        <c:when test="${edit}">
                            <form:input type="text" path="ssoId" id="ssoId" disabled="true"/>
                        </c:when>
                        <c:otherwise>
                            <form:input type="text" path="ssoId" id="ssoId"/>
                            <div>
                                <form:errors path="ssoId"/>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="password">Password</label>
                <div>
                    <form:input type="password" path="password" id="password"/>
                    <div>
                        <form:errors path="password"/>
                    </div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="email">Email</label>
                <div>
                    <form:input type="text" path="email" id="email"/>
                    <div>
                        <form:errors path="email"/>
                    </div>
                </div>
            </div>
        </div>

        <!-- Change type of user is available only for admins -->
        <div>
            <sec:authorize access="hasRole('ADMIN')">
                <div>
                    <label for="userProfiles">Roles</label>
                    <div>
                        <form:select path="userProfiles" items="${roles}" multiple="true" itemValue="id"
                                     itemLabel="type"/>
                    </div>
                </div>
            </sec:authorize>
        </div>

        <div>
            <div>
                <c:choose>
                    <c:when test="${edit}">
                        <input type="submit" value="Update"/> or <a
                            href="<c:url value='/listUsers' />">Cancel</a>
                    </c:when>
                    <c:otherwise>
                        <input type="submit" value="Register"/> or <a
                            href="<c:url value='/listUsers' />">Cancel</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </form:form>
</div>
</body>
</html>