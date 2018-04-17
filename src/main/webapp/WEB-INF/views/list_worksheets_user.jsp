<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Worksheets of user</title>
</head>

<body>

<div>
    <%@include file="auth_header.jsp" %>
    <div>

        <p>Here you can track status of your worksheet</p>

        <table>
            <tr>
                <th>Name</th>
                <th>Date</th>
                <th>Status</th>
            </tr>

            <tr>
                <!-- Formatting date -->
                <td>
                    ${worksheet.user.firstName}
                </td>
                <td>
                    ${worksheet.submissionDate}
                </td>
                <td>
                    ${worksheet.status}
                </td>
                <td>
                    <a href="<c:url value='/editWorksheet-${worksheet.id}' />">edit</a>
                </td>
            </tr>
        </table>

    </div>
</div>

</body>
</html>