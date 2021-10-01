<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table border="1">
    <tr>
        <td><label for="cpf">CPF</label></td>
        <td><input type="text" id="cpf" name="cpf" size="45" required
                   value="${requestScope.cliente.cpf}"
                <c:if test="${requestScope.cliente != null}"> readonly </c:if>/></td>
    </tr>

    <tr>
        <td><label for="nome"><fmt:message key="nome"/></label></td>
        <td><input type="text" id="nome" name="nome" size="45" required
                   value="${requestScope.cliente.nome}"/></td>
    </tr>
    <tr>
        <td><label for="email"><fmt:message key="email"/></label></td>
        <td><input type="text" id="email" name="email" size="45" required
                   value="${requestScope.cliente.email}"/></td>
    </tr>
    <tr>
        <td><label for="senha"><fmt:message key="senha"/></label></td>
        <td><input type="text" id="senha" name="senha" size="45" required
                   value="${requestScope.cliente.senha}"/></td>
    </tr>
    <tr>
        <td><label for="sexo"><fmt:message key="sexo"/></label></td>
        <td><input type="text" id="sexo" name="sexo" size="1"
                   value="${requestScope.cliente.sexo}"/></td>
    </tr>
    <tr>
        <td><label for="telefone"><fmt:message key="telefone"/></label></td>
        <td><input type="text" id="telefone" name="telefone" size="45" required
                   value="${requestScope.cliente.telefone}"/></td>
    </tr>
    <tr>
        <td><label for="dataNascimento"><fmt:message key="data_nascimento"/></label></td>
        <td><input type="date" id="dataNascimento" name="dataNascimento" required
                   value="${requestScope.cliente.dataNascimento}"/></td>
    </tr>
    <tr>
        <td><label for="papel"><fmt:message key="papel"/></label></td>
        <td><select id="papel" name="papel" required>
            <option value="CLIENTE"><fmt:message key="cliente"/></option>
            <option value="ADMIN"><fmt:message key="admin"/></option>
        </select>
        </td>
    </tr>

    <tr>
        <td colspan="2" align="center"><input type="submit" value="<fmt:message key="enviar"/>"/></td>
    </tr>
</table>
