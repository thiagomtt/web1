<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<fmt:bundle basename="msg">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Autorização</title>
        <link href="${pageContext.request.contextPath}/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
    <%
        String contextPath = request.getContextPath().replace("/", "");
    %>
    <h1>Autorização</h1>
    <c:if test="${mensagens.existeErros}">
        <div id="erro">
            <ul>
                <c:forEach var="erro" items="${mensagens.erros}">
                    <li> ${erro} </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <h2>
        <a href="/<%=contextPath%>"><fmt:message key="homepage"/></a>
        &nbsp;&nbsp;&nbsp;
        <a href="/<%=contextPath%>/login"><fmt:message key="login"/></a>
    </h2>
    </body>
</fmt:bundle>
</html>