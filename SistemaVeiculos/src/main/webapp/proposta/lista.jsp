<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:bundle basename="msg">
    <head>
        <title><fmt:message key="lista_propostas"/></title>
    </head>
    <body>
    <%
        String contextPath = request.getContextPath().replace("/", "");
    %>

    <div style="text-align: center">
        <h2>
            <a href="/<%=contextPath%>"><fmt:message key="homepage"/></a>
            <c:if test="${sessionScope.clienteLogado == null and sessionScope.lojaLogado == null}">
                &nbsp;&nbsp;&nbsp;
                <c:redirect url="/propostas/noAuth"/>
            </c:if>
        </h2>
    </div>
    <div align="center">
        <c:choose>
            <c:when test="${sessionScope.clienteLogado != null}">
                <table border="1">
                    <tr>
                        <th>CPF</th>
                        <th><fmt:message key="modelo"/></th>
                        <th>Chassi</th>
                        <th><fmt:message key="loja"/></th>
                        <th><fmt:message key="valor"/></th>
                        <th><fmt:message key="condicao_pagamento"/></th>
                        <th><fmt:message key="data_envio"/></th>
                        <th><fmt:message key="status"/></th>
                    </tr>
                    <c:forEach var="proposta" items="${requestScope.listaPropostas}">
                        <c:if test="${proposta.cliente.cpf == sessionScope.clienteLogado.cpf}">
                            <tr>
                                <td>${proposta.cliente.cpf}</td>
                                <td>${proposta.veiculo.modelo}</td>
                                <td>${proposta.veiculo.chassi}</td>
                                <td>${proposta.loja.nome}</td>
                                <td>${proposta.valor}</td>
                                <td>${proposta.pagamento}</td>
                                <td>${proposta.data}</td>
                                <td>
                                    <c:if test="${proposta.status == 3}"><fmt:message key="contraproposta"/></c:if>
                                    <c:if test="${proposta.status == 2}"><fmt:message key="analise"/></c:if>
                                    <c:if test="${proposta.status == 1}"><fmt:message key="aceita"/></c:if>
                                    <c:if test="${proposta.status == 0}"><fmt:message key="recusada"/></c:if>
                                </td>
                                <c:if test="${proposta.status != 1 and proposta.status != 0}">
                                    <td>
                                        <a href="/<%= contextPath%>/propostas/remove?cpf=${proposta.cliente.cpf}&chassi=${proposta.veiculo.chassi}"
                                           onclick="return confirm(<fmt:message key="certeza_exclusao"/>);"><fmt:message
                                                key="cancelar"/></a>
                                    </td>
                                </c:if>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <table border="1">
                    <tr>
                        <th>CPF</th>
                        <th><fmt:message key="cliente"/></th>
                        <th><fmt:message key="modelo"/></th>
                        <th>Chassi</th>
                        <th><fmt:message key="valor"/></th>
                        <th><fmt:message key="condicao_pagamento"/></th>
                        <th><fmt:message key="data_envio"/></th>
                        <th><fmt:message key="status"/></th>
                    </tr>
                    <c:forEach var="proposta" items="${requestScope.listaPropostas}">
                        <c:if test="${proposta.loja.cnpj == sessionScope.lojaLogado.cnpj}">
                            <tr>
                                <td>${proposta.cliente.cpf}</td>
                                <td>${proposta.cliente.nome}</td>
                                <td>${proposta.veiculo.modelo}</td>
                                <td>${proposta.veiculo.chassi}</td>
                                <td>${proposta.valor}</td>
                                <td>${proposta.pagamento}</td>
                                <td>${proposta.data}</td>
                                <td>
                                    <c:if test="${proposta.status == 3}"><fmt:message key="contraproposta"/></c:if>
                                    <c:if test="${proposta.status == 2}"><fmt:message key="analise"/></c:if>
                                    <c:if test="${proposta.status == 1}"><fmt:message key="aceita"/></c:if>
                                    <c:if test="${proposta.status == 0}"><fmt:message key="recusada"/></c:if>
                                </td>
                                <c:if test="${proposta.status == 2}">
                                    <td>
                                        <a href="/<%=contextPath%>/propostas/edita?cpf=${proposta.cliente.cpf}&chassi=${proposta.veiculo.chassi}">
                                            <fmt:message key="responder"/></a>
                                    </td>
                                </c:if>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>

    </div>


    </body>
</fmt:bundle>
</html>
