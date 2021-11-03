<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:bundle basename="msg">
    <head>
        <c:choose>
            <c:when test="${requestScope.loja != null}">
                <title><fmt:message key="editar_loja"/></title>
            </c:when>
            <c:otherwise>
                <title><fmt:message key="cadastro_loja"/></title>
            </c:otherwise>
        </c:choose>
    </head>
    <body>
    <div style="text-align: center">
        <h2>
            <a href="${pageContext.request.contextPath}/lojas/"><fmt:message key="gerenciar_lojas"/></a>
        </h2>
    </div>
    <c:if test="${mensagens.existeErros}">
        <div id="erro">
            <ul>
                <c:forEach var="erro" items="${mensagens.erros}">
                    <li> ${erro} </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <div style="display: flex; justify-content: center">
        <c:choose>
            <c:when test="${requestScope.loja != null}">
                <form action="atualiza" method="post">
                    <%@include file="campos.jsp" %>
                </form>
            </c:when>
            <c:otherwise>
                <form action="insere" method="post">
                    <%@include file="campos.jsp" %>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
    </body>
</fmt:bundle>
</html>