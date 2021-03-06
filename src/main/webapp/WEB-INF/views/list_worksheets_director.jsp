<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Worksheets for director</title>
</head>

<body>

<div>
    <%@include file="auth_header.jsp" %>
    <div>

        <span>
            <h4>Worksheets for director</h4>
        </span>

        <table id="tableWithWorksheets">
            <tr>
                <th>First name</th>
                <th>Last name</th>
                <th>Date</th>
            </tr>
            <c:forEach items="${worksheets}" var="worksheet">
                <tr>
                    <td>${worksheet.user.firstName}</td>
                    <td>${worksheet.user.lastName}</td>
                    <td>${worksheet.submissionDate}</td>
                    <td><a href="<c:url value='/viewWorksheet-${worksheet.id}' />">view</a></td>
                    <td><a href="<c:url value='/declineWorksheetDirector-${worksheet.id}' />">decline</a></td>
                    <td><a href="<c:url value='/offerWorksheet-${worksheet.id}' />">offer!</a></td>
                </tr>
            </c:forEach>
        </table>

    </div>
</div>

</body>
</html>