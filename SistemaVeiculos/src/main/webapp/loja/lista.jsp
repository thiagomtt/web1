<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:bundle basename="msg">
    <head>
        <title><fmt:message key="gerenciar_lojas"/></title>
    </head>
    <body>
    <%
        String contextPath = request.getContextPath().replace("/", "");
    %>
    <div align="center">
        <h1><fmt:message key="gerenciar_lojas"/></h1>
        <h2>
            <a href="/<%=contextPath%>"><fmt:message key="homepage"/></a>
            &nbsp;&nbsp;&nbsp; <a
                href="/<%=contextPath%>/lojas/cadastro"><fmt:message key="adicionar_loja"/></a>
        </h2>
        <h2>
            <c:if test="${sessionScope.clienteLogado.papel != \"ADMIN\" or sessionScope.lojaLogado != null}">
                &nbsp;&nbsp;&nbsp;
                <c:redirect url="/lojas/noAuth"/>
            </c:if>
        </h2>
    </div>
    <div align="center">
        <table border="1">
            <tr>
                <th>CNPJ</th>
                <th><fmt:message key="nome"/></th>
                <th><fmt:message key="email"/></th>
                <th><fmt:message key="senha"/></th>
                <th><fmt:message key="descricao"/></th>
            </tr>
            <c:forEach var="loja" items="${requestScope.listaLojas}">
                <tr>
                    <td>${loja.cnpj}</td>
                    <td>${loja.nome}</td>
                    <td>${loja.email}</td>
                    <td>${loja.senha}</td>
                    <td>${loja.descricao}</td>
                    <td><a href="/<%=contextPath%>/lojas/edita?cnpj=${loja.cnpj}"><fmt:message key="editar"/></a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/<%=contextPath%>/lojas/remove?cnpj=${loja.cnpj}"
                           onclick="return confirm(<fmt:message key="certeza_exclusao"/>);"><fmt:message
                                key="remover"/></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    </body>
</fmt:bundle>
</html>