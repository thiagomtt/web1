<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="msg">
    <table border="1">
        <tr>
            <td><label for="cnpj">CNPJ</label></td>
            <td><input type="text" id="cnpj" name="cnpj" size="45" required
                       value="${requestScope.loja.cnpj}"
                    <c:if test="${requestScope.loja != null}"> readonly </c:if>/></td>
        </tr>

        <tr>
            <td><label for="nome"><fmt:message key="nome"/></label></td>
            <td><input type="text" id="nome" name="nome" size="45" required
                       value="${requestScope.loja.nome}"/></td>
        </tr>
        <tr>
            <td><label for="email"><fmt:message key="email"/></label></td>
            <td><input type="text" id="email" name="email" size="45" required
                       value="${requestScope.loja.email}"/></td>
        </tr>
        <tr>
            <td><label for="senha"><fmt:message key="senha"/></label></td>
            <td><input type="text" id="senha" name="senha" size="45" required
                       value="${requestScope.loja.senha}"/></td>
        </tr>
        <tr>
            <td><label for="descricao"><fmt:message key="descricao"/></label></td>
            <td><input type="text" id="descricao" name="descricao" size="45" required
                       value="${requestScope.loja.descricao}"/></td>

        <tr>
            <td colspan="2" style="text-align: center"><input type="submit" value="<fmt:message key="enviar"/>"/></td>
        </tr>
    </table>
</fmt:bundle>