<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html style="display: flex; justify-content: center">
<fmt:bundle basename="msg">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="script.js"></script>
        <link href="${pageContext.request.contextPath}/style.css" rel="stylesheet" type="text/css"/>
        <title>
            <fmt:message key="login"/>
        </title>
    </head>
    <body>
    <% String contextPath = request.getContextPath().replace("/", ""); %>

    <h2>
        <a href="/<%=contextPath%>"><fmt:message key="homepage"/></a>
    </h2>

    <c:if test="${mensagens.existeErros}">
        <div id="erro">
            <ul>
                <c:forEach var="erro" items="${mensagens.erros}">
                    <li> ${erro} </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <div id="btn" class="btn" onClick="doToggle()">
        <div class="none" id="loja" style="text-align: center;">
            <fmt:message key="login_cliente"/>
        </div>
        <div id="cliente" style="text-align: center;">
            <fmt:message key="login_loja"/>
        </div>
    </div>

    <fieldset id="logCli">
        <b><fmt:message key="cliente"/></b>
        <form name="teste" action="/<%= contextPath%>/login" method="POST">
            <div>
                <div>
                    <p><fmt:message key="email"/></p> <input type="text" name="email" required/>
                    <p><fmt:message key="senha"/></p> <input type="password" name="senha" required/>
                </div>
                <input type="submit" name="loginCliente"
                       value=<fmt:message key="login"/>/>
            </div>
        </form>
    </fieldset>

    <fieldset class="none" id="logLoja">
        <b><fmt:message key="loja"/></b>
        <form name="teste" action="/<%= contextPath%>/login" method="POST">
            <div>
                <div>
                    <p><fmt:message key="email"/></p> <input type="text" name="email"/>
                    <p><fmt:message key="senha"/></p> <input type="password" name="senha"/>
                </div>
                <input type="submit" name="loginLoja"
                       value=<fmt:message key="login"/>/>
            </div>
        </form>
    </fieldset>

    </body>
</fmt:bundle>

</html>