<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
    .none {
        display: none;
    }
</style>

<fmt:bundle basename="msg">
    <table border="1">
        <caption>
            <c:choose>
                <c:when test="${requestScope.proposta != null}">
                    <fmt:message key="analise_proposta"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="faca_proposta"/>
                </c:otherwise>
            </c:choose>
        </caption>

        <c:choose>
            <c:when test="${sessionScope.clienteLogado != null}">
                <tr>
                    <td><label for="cpf">CPF</label></td>
                    <td><input type="text" id="cpf" name="cpf" size="25" readonly required
                               value="${sessionScope.clienteLogado.cpf}"/></td>
                </tr>
                <tr>
                    <td><label for="chassi">Chassi</label></td>
                    <td><input type="text" id="chassi" name="chassi" size="25" readonly required
                               value="${requestScope.veiculo.chassi}"></td>
                </tr>
                <tr>
                    <td><label for="loja"><fmt:message key="loja"/></label></td>
                    <td><input type="text" id="loja" name="loja" size="25" readonly required
                               value="${requestScope.veiculo.loja.nome}"></td>
                </tr>
                <tr>
                    <td><label for="cnpj">CNPJ <fmt:message key="loja"/></label></td>
                    <td><input type="text" id="cnpj" name="cnpj" size="25" readonly required
                               value="${requestScope.veiculo.loja.cnpj}"></td>
                </tr>
                <tr>
                    <td><label for="valor"><fmt:message key="valor"/></label></td>
                    <td><input type="number" id="valor" name="valor" size="25" required
                               value="${requestScope.proposta.valor}"/></td>
                </tr>
                <tr>
                    <td><label for="pagamento"><fmt:message key="condicao_pagamento"/></label></td>
                    <td><select id="pagamento" name="pagamento" required>
                        <option value="A VISTA"><fmt:message key="avista"/></option>
                        <option value="PARCELADO"><fmt:message key="parcelado"/></option>
                        <option value="FINANCIAMENTO"><fmt:message key="financiamento"/></option>
                    </select>
                    </td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr style="display: none">
                    <td><label for="descricao"><fmt:message key="descricao"/></label></td>
                    <td><input type="text" id="descricao" name="descricao" readonly required
                               value="${requestScope.veiculo.descricao}"></td>
                </tr>
                <tr style="display: none">
                    <td><label for="cnpj">CNPJ</label></td>
                    <td><input type="text" id="cnpj" name="cnpj" readonly required
                               value="${sessionScope.lojaLogado.cnpj}"></td>
                </tr>
                <tr style="display: none">
                    <td><label for="emailCliente"><fmt:message key="email"/></label></td>
                    <td><input type="text" id="emailCliente" name="emailCliente" readonly required
                               value="${requestScope.proposta.cliente.email}"></td>
                </tr>
                <tr>
                    <td><label for="cpf">CPF</label></td>
                    <td><input type="text" id="cpf" name="cpf" size="25" readonly required
                               value="${requestScope.proposta.cliente.cpf}"/></td>
                </tr>
                <tr>
                    <td><label for="nomeCliente"><fmt:message key="cliente"/></label></td>
                    <td><input type="text" id="nomeCliente" name="nomeCliente" size="25" readonly required
                               value="${requestScope.proposta.cliente.nome}"></td>
                </tr>
                <tr>
                    <td><label for="modelo"><fmt:message key="modelo"/></label></td>
                    <td><input type="text" id="modelo" name="modelo" size="25" readonly required
                               value="${requestScope.veiculo.modelo}"></td>
                </tr>
                <tr>
                    <td><label for="chassi">Chassi</label></td>
                    <td><input type="text" id="chassi" name="chassi" size="25" readonly required
                               value="${requestScope.veiculo.chassi}"></td>
                </tr>
                <tr>
                    <td><label for="status"><fmt:message key="status"/></label></td>
                    <td><select id="status" name="status" required onchange='contraProposta(value)'>
                        <option value="1"><fmt:message key="aceita"/></option>
                        <option value="3"><fmt:message key="contraproposta"/></option>
                        <option value="0"><fmt:message key="recusar"/></option>
                    </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="valor"><fmt:message key="valor"/></label></td>
                    <td><input type="number" id="valor" name="valor" size="25" required readonly
                               value="${requestScope.proposta.valor}"/></td>
                </tr>
                <tr>
                    <td><label for="pagamento"><fmt:message key="condicao_pagamento"/></label></td>
                    <td><select id="pagamento" name="pagamento" required>
                        <option value="A VISTA"><fmt:message key="avista"/></option>
                        <option value="PARCELADO"><fmt:message key="parcelado"/></option>
                        <option value="FINANCIAMENTO"><fmt:message key="financiamento"/></option>
                    </select>
                    </td>
                </tr>
                <tr id="campoObservacao" class="none">
                    <td><label for="observacao"><fmt:message key="observacao"/></label></td>
                    <td><textarea id="observacao" name="observacao" rows="5" cols="26"></textarea></td>
                </tr>
            </c:otherwise>
        </c:choose>

        <tr>
            <td colspan="2" style="text-align: center"><input type="submit" value="<fmt:message key="enviar"/>"/></td>
        </tr>

        <script>
            function contraProposta(value) {
                var campo = document.querySelector("#campoObservacao")
                if (value === "3") {
                    campo.classList.remove("none")
                    document.querySelector("#valor").readOnly = false
                } else {
                    campo.classList.add("none")
                    document.querySelector("#valor").readOnly = true
                }
            }
        </script>
    </table>
</fmt:bundle>
