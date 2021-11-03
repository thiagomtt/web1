<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <title>Home</title>
    <meta charset="UTF-8">
</head>

<body>

<%
    String contextPath = request.getContextPath().replace("/", "");
%>

<fmt:bundle basename="messages">


    <a class="btn" href="/<%= contextPath%>/login.jsp">Login</a>

    <c:if test="${mensagens.existeErros}">
        <div id="erro">
            <ul>
                <c:forEach var="erro" items="${mensagens.erros}">
                    <li> ${erro} </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <a href="/<%= contextPath%>/">HomePage</a>


</fmt:bundle>
</body>

</html>