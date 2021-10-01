<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<fmt:bundle basename="msg">
    <head>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
        <title>
            <fmt:message key="homepage"/>
        </title>
    </head>
    <body>
    <% String contextPath = request.getContextPath().replace("/", "");
        String contextServlet = application.getContextPath();%>

    <c:if test="${mensagens.existeErros}">
        <div id="erro">
            <ul>
                <c:forEach var="erro" items="${mensagens.erros}">
                    <li> ${erro} </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <div class="top-bar">
        <h1 class="title">NATHIJOR Veículos</h1>

        <div class="nav-bar">

            <div id="filter">
                <a class="btn" href="/<%=contextPath%>/veiculos/lista">Todos</a>
                <c:forEach var="modelo" items="${requestScope.listaModelos}">
                    <a href="/<%=contextPath%>/veiculos/lista?modelo=${modelo}"
                       class="btn" title="Filtro">
                            ${modelo}
                    </a>
                </c:forEach>
            </div>

            <div class="login-cadastro">
                <c:if test="${sessionScope.lojaLogado != null}">
                    <a href="/<%=contextPath%>/propostas" style="margin-right: 10px;">
                        <fmt:message key="lista_propostas"/>
                    </a>
                    <a href="/<%=contextPath%>/veiculos/cadastro" style="margin-right: 10px;">
                        <fmt:message key="cadastro_veiculo"/>
                    </a>
                </c:if>
                <c:if test="${sessionScope.clienteLogado.papel == \"CLIENTE\"}">
                    <a href="/<%=contextPath%>/propostas" style="margin-right: 10px;">
                        <fmt:message key="minhas_propostas"/>
                    </a>
                </c:if>
                <c:if test="${sessionScope.clienteLogado.papel == \"ADMIN\"}">
                    <a href="/<%=contextPath%>/lojas" style="margin-right: 10px;">
                        <fmt:message key="gerenciar_lojas"/>
                    </a>
                    <a href="/<%=contextPath%>/clientes" style="margin-right: 10px;">
                        <fmt:message key="gerenciar_clientes"/>
                    </a>
                </c:if>
                <c:choose>
                    <c:when test="${sessionScope.clienteLogado == null && sessionScope.lojaLogado == null}">
                        <a href="/<%=contextPath%>/login.jsp">
                            <fmt:message key="login"/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="/<%=contextPath%>/logout">
                            <fmt:message key="logout"/>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </div>

    <!-- TAB DO ESTOQUE -->
    <c:choose>
        <c:when test="${sessionScope.clienteLogado == null && sessionScope.lojaLogado == null || sessionScope.clienteLogado != null}">
            <div class="grid-anuncios">
                <c:forEach var="veiculo" items="${requestScope.listaVeiculos}">
                    <div class="veiculosEstoque">
                        <div class="carro-img " style="padding: 5px;">
                            <a href="/<%=contextPath%>/propostas/cadastro?chassi=${veiculo.chassi}">
                                <img src="<%=contextServlet%>/fotos/${veiculo.chassi}_1.jpg"/>
                            </a>
                        </div>
                        <div class="carro-info">
                            <h2 class="">
                                <a href="/<%=contextPath%>/propostas/cadastro?chassi=${veiculo.chassi}"
                                   title="titulo carro"><span>${veiculo.modelo} </span>${veiculo.ano}</a>
                            </h2>
                            <ul class="list-inline resu-veiculo">
                                <li class="text-center border-right">
                            <span class="resumo">
                                    ${veiculo.descricao}
                            </span>
                                </li>
                                <li class="text-center border-right">
                            <span class="resumo">
                                    ${veiculo.km} KM
                            </span>
                                </li>
                            </ul>
                            <!-- anunciante -->
                            <div class="anunciante margin-top-10">
                        <span>
                                ${veiculo.loja.nome}
                        </span>
                            </div>
                        </div>
                        <div class="carro-preco">
                            <h3 class="preco">R$
                                <span>${veiculo.valor}</span>
                            </h3>
                        </div>
                        <div class="gerenciar" style="display: flex; justify-content: space-between">
                            <c:if test="${sessionScope.clienteLogado.papel == \"CLIENTE\"}">
                                <a href="/<%=contextPath%>/propostas/cadastro?chassi=${veiculo.chassi}" class="btn"
                                   title="Proposta">
                                    <fmt:message key="proposta"/>
                                </a>
                            </c:if>

                            <c:if test="${sessionScope.clienteLogado.papel == \"ADMIN\"}">
                                <a href="/<%=contextPath%>/veiculos/remove?chassi=${veiculo.chassi}"
                                   class="btn" title="Remove">
                                    <fmt:message key="deletar_veiculo"/>
                                </a>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="grid-anuncios">
                <c:forEach var="veiculo" items="${requestScope.listaVeiculos}">
                    <c:if test="${veiculo.loja.cnpj == sessionScope.lojaLogado.cnpj}">
                        <div class="veiculosEstoque">
                            <div class="carro-img " style="padding: 5px;">
                                <img src="${pageContext.request.contextPath}/fotos/${veiculo.chassi}_1.jpg"/>
                            </div>
                            <div class="carro-info">
                                <h2 class="">
                                    <span>${veiculo.modelo} </span>${veiculo.ano}
                                </h2>
                                <ul class="list-inline resu-veiculo">
                                    <li class="text-center border-right">
                            <span class="resumo">
                                    ${veiculo.descricao}
                            </span>
                                    </li>
                                    <li class="text-center border-right">
                            <span class="resumo">
                                    ${veiculo.km} KM
                            </span>
                                    </li>
                                </ul>
                                <!-- anunciante -->
                                <div class="anunciante margin-top-10">
                        <span>
                                ${veiculo.loja.nome}
                        </span>
                                </div>
                            </div>
                            <div class="carro-preco">
                                <h3 class="preco">R$
                                    <span>${veiculo.valor}</span>
                                </h3>
                            </div>
                            <div class="gerenciar" style="display: flex; justify-content: space-between">
                                <a href="/<%=contextPath%>/veiculos/edita?chassi=${veiculo.chassi}"
                                   class="btn" title="Editar">
                                    <fmt:message key="editar_veiculo"/>
                                </a>

                                <a href="/<%=contextPath%>/veiculos/remove?chassi=${veiculo.chassi}"
                                   class="btn" title="Remove">
                                    <fmt:message key="deletar_veiculo"/>
                                </a>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
    <!-- / TAB DO ESTOQUE -->
    </body>
</fmt:bundle>
</html>
