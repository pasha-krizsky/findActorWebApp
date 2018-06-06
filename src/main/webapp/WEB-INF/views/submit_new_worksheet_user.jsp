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
    <div id="newWorksheetHeader">
        Submit new worksheet
    </div>
    <form:form method="POST" modelAttribute="worksheet">
        <form:input type="hidden" path="id" id="id"/>
        <div>
            <div>
                <label for="age">Age</label>
                <div>
                    <form:input type="text" path="age" id="age"/>
                    <div><form:errors path="age"/></div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="height">Height</label>
                <div>
                    <form:input type="text" path="height" id="height"/>
                    <div><form:errors path="height"/></div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="weight">Weight</label>
                <div>
                    <form:input type="text" path="weight" id="weight"/>
                    <div><form:errors path="weight"/></div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="sex">Sex</label>
                <div>
                    <form:input type="text" path="sex" id="sex"/>
                    <div><form:errors path="sex"/></div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="nationality">Nationality</label>
                <div>
                    <form:input type="text" path="nationality" id="nationality"/>
                    <div><form:errors path="nationality"/></div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="eyeColor">Eye color</label>
                <div>
                    <form:input type="text" path="eyeColor" id="eyeColor"/>
                    <div><form:errors path="eyeColor"/></div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="skinColor">Skin color</label>
                <div>
                    <form:input type="text" path="skinColor" id="skinColor"/>
                    <div><form:errors path="skinColor"/></div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="hairColor">Hair color</label>
                <div>
                    <form:input type="text" path="hairColor" id="hairColor"/>
                    <div><form:errors path="hairColor"/></div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="experience">Experience</label>
                <div>
                    <form:input type="text" path="experience" id="experience"/>
                    <div><form:errors path="experience"/></div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <label for="reason">Reason</label>
                <div>
                    <form:input type="text" path="reason" id="reason"/>
                    <div><form:errors path="reason"/></div>
                </div>
            </div>
        </div>

        <div>
            <div>
                <input id="submitWorksheetButton" type="submit" value="submit"/>
            </div>
        </div>
    </form:form>
</div>

</body>
</html>