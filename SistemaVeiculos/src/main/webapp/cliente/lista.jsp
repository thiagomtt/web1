<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:bundle basename="msg">
    <head>
        <title><fmt:message key="gerenciar_clientes"/></title>
    </head>
    <body>
    <%
        String contextPath = request.getContextPath().replace("/", "");
    %>
    <div style="text-align: center">
        <h1><fmt:message key="gerenciar_clientes"/></h1>
        <h2>
            <a href="/<%=contextPath%>"><fmt:message key="homepage"/></a>
            &nbsp;&nbsp;&nbsp;
            <a href="/<%=contextPath%>/clientes/cadastro"><fmt:message key="adicioanr_cliente"/></a>
        </h2>
        <h2>
            <c:if test="${sessionScope.clienteLogado.papel != \"ADMIN\" or sessionScope.lojaLogado != null}">
                &nbsp;&nbsp;&nbsp;
                <c:redirect url="/veiculos/noAuth"/>
            </c:if>
        </h2>
    </div>
    <div align="center">
        <table border="1">
            <tr>
                <th>CPF</th>
                <th><fmt:message key="nome"/></th>
                <th><fmt:message key="email"/></th>
                <th><fmt:message key="senha"/></th>
                <th><fmt:message key="sexo"/></th>
                <th><fmt:message key="telefone"/></th>
                <th><fmt:message key="data_nascimento"/></th>
                <th><fmt:message key="papel"/></th>
            </tr>
            <c:forEach var="cliente" items="${requestScope.listaClientes}">
                <tr>
                    <td>${cliente.cpf}</td>
                    <td>${cliente.nome}</td>
                    <td>${cliente.email}</td>
                    <td>${cliente.senha}</td>
                    <td>${cliente.sexo}</td>
                    <td>${cliente.telefone}</td>
                    <td>${cliente.dataNascimento}</td>
                    <td>${cliente.papel}</td>
                    <td><a href="/<%= contextPath%>/clientes/edita?cpf=${cliente.cpf}"><fmt:message key="editar"/></a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/<%= contextPath%>/clientes/remove?cpf=${cliente.cpf}"
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