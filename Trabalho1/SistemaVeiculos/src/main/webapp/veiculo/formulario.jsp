<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:bundle basename="msg">
    <head>
        <title><fmt:message key="cadastro_veiculo"/></title>
    </head>
    <body>
    <div align="center">
        <h2>
            <a href="${pageContext.request.contextPath}/veiculos/"><fmt:message key="homepage"/></a>
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
    <div align="center">
        <c:choose>
            <c:when test="${requestScope.veiculo != null}">
                <form action="atualiza" method="post">
                    <%@include file="campos.jsp" %>
                </form>
            </c:when>
            <c:otherwise>
                <form action="insere" method="post" enctype="multipart/form-data">
                    <%@include file="campos.jsp" %>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
    </body>
</fmt:bundle>
</html>