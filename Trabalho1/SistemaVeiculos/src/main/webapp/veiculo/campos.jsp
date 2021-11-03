<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="msg">
    <style>
        .none {
            display: none;
        }
    </style>

    <table border="1">

        <c:if test="${requestScope.veiculo == null}">
            <tr>
                <td><label for="modelo"><fmt:message key="modelo"/></label></td>
                <td><input type="text" id="modelo" name="modelo" size="45" required
                           value="${requestScope.veiculo.modelo}"/></td>
            </tr>
            <tr>
                <td><label for="ano"><fmt:message key="ano"/></label></td>
                <td><input type="number" id="ano" name="ano" size="5" required
                           min="1900" value="${requestScope.veiculo.ano}"/></td>
            </tr>
        </c:if>
        <c:if test="${requestScope.veiculo != null}">
            <tr>
                <td><label for="loja"><fmt:message key="loja"/></label></td>
                <td><select id="loja" name="loja">
                    <c:forEach var="loja" items="${requestScope.lojas}">
                        <option value="${loja.key}"
                            ${requestScope.veiculo.loja.nome==loja.value ? 'selected' :
                                    '' }>${loja.value}</option>
                    </c:forEach>
                </select></td>
            </tr>
        </c:if>
        <tr>
            <td><label for="chassi">Chassi</label></td>
            <td><input type="text" id="chassi" name="chassi" size="45" required
                       value="${requestScope.veiculo.chassi}"
                    <c:if test="${requestScope.veiculo != null}"> readonly </c:if>/></td>
        </tr>
        <tr>
            <td><label for="placa">Placa</label></td>
            <td><input type="text" id="placa" name="placa" size="45"
                       required value="${requestScope.veiculo.placa}"/></td>
        </tr>
        <tr>
            <td><label for="km">Km</label></td>
            <td><input type="number" id="km" name="km" required
                       min="0" step="any" value="${requestScope.veiculo.km}"/></td>
        </tr>
        <tr>
            <td><label for="descricao"><fmt:message key="descricao"/></label></td>
            <td><input type="text" id="descricao" name="descricao" size="45" required
                       value="${requestScope.veiculo.descricao}"/></td>
        </tr>
        <tr>
            <td><label for="valor"><fmt:message key="valor"/></label></td>
            <td><input type="number" id="valor" name="valor" required
                       min="0.01" step="any" value="${requestScope.veiculo.valor}"/></td>
        </tr>
        <c:if test="${requestScope.veiculo == null}">
            <tr>
                <td><label for="imagens"><fmt:message key="imagens"/></label></td>
                <td><input type="file" id="imagens" name="imagens" multiple onchange="checkNumFiles();"/></td>
            </tr>
        </c:if>
        <tr id="enviar">
            <td colspan="2" style="text-align: center"><input type="submit" value="<fmt:message key="enviar"/>"/></td>
        </tr>

        <script>
            var selectedFiles = [];

            function deleteFile(idx) {
                console.log(idx);
                selectedFiles.splice(idx, 1);
                updateFront();
            }

            function onFileChange() {
                const files = document.getElementById('imagens').files;

                console.log(files);

                for (var i = 0; i < files.length; i++) {
                    selectedFiles.push(files[i].name);
                }

                updateFront();
            }

            function updateFront() {
                console.log(selectedFiles)
                const list = document.getElementById('lista');
                var children = '';

                for (var i = 0; i < selectedFiles.length; i++) {
                    children += "<div><span>" + selectedFiles[i] + "</span><button onclick=\"deleteFile(" + i + ")\">delete</button></div>"
                }

                list.innerHTML = children;
            }

            function checkNumFiles() {
                var numFiles = document.getElementById("imagens").files.length;
                if (numFiles > 10) {
                    alert("Limite de fotos excedido!\nMáximo - 10 Fotos por carro");
                    document.getElementById("enviar").classList.add("none");
                } else {
                    document.getElementById("enviar").classList.remove("none");
                }
            }
        </script>
    </table>
</fmt:bundle>