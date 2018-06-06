<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login page</title>
</head>

<body>

<div class="wrapper">
    <c:url var="loginUrl" value="/login"/>
    <form class="form-signin" action="${loginUrl}" method="post">
        <h2 class="form-signin-heading">Please login</h2>
        <c:if test="${param.error != null}">
            <p>Invalid username and password.</p>
        </c:if>
        <c:if test="${param.logout != null}">
            <p>You have been logged out successfully.</p>
        </c:if>
        <div>
            <label for="username"></label>
            <input type="text" class="form-control" id="username" name="ssoId" placeholder="Enter Username" required>
        </div>
        <div>
            <label for="password"></label>
            <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password"
                   required>
        </div>
        <div>
            <label class="checkbox"><input type="checkbox" id="rememberme" name="remember-me"> Remember Me</label>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>
        <div>
            <button id="submitLoginButton" class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
        </div>
        <div>
            <a href="/registerUser">Register new user</a>
        </div>
    </form>
</div>

</body>
</html>