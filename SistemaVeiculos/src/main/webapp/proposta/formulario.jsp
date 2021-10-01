<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<fmt:bundle basename="msg">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style>
            * {
                box-sizing: border-box
            }

            body {
                font-family: Verdana, sans-serif;
                margin: 0
            }

            #erro {
                width: 85%;
                margin: 20px;
                border: 1px solid red;
                background-color: beige;
            }

            .mySlides {
                display: none
            }

            img {
                vertical-align: middle;
            }

            /* Slideshow container */
            .slideshow-container {
                max-width: 500px;
                max-height: 500px;
                position: relative;
            }

            .tudo {
                display: flex;
                gap: 30px;
                justify-content: center;
                width: 100%;
            }

            .formulario {
                height: 100%;
                align-self: center;
            }

            /* Next & previous buttons */
            .prev, .next {
                cursor: pointer;
                position: absolute;
                top: 50%;
                width: auto;
                padding: 16px;
                margin-top: -22px;
                color: white;
                font-weight: bold;
                font-size: 18px;
                transition: 0.6s ease;
                border-radius: 0 3px 3px 0;
                user-select: none;
            }

            /* Position the "next button" to the right */
            .next {
                right: 0;
                border-radius: 3px 0 0 3px;
            }

            /* On hover, add a black background color with a little bit see-through */
            .prev:hover, .next:hover {
                background-color: rgba(0, 0, 0, 0.8);
            }

            /* Caption text */
            .text {
                color: #f2f2f2;
                font-size: 15px;
                padding: 8px 12px;
                position: absolute;
                bottom: 8px;
                width: 100%;
                text-align: center;
            }

            /* Number text (1/3 etc) */
            .numbertext {
                color: #f2f2f2;
                background-color: rgba(0, 0, 0, 0.5);
                font-size: 12px;
                padding: 8px 12px;
                position: absolute;
                top: 0;
            }

            /* The dots/bullets/indicators */
            .dot {
                cursor: pointer;
                height: 15px;
                width: 15px;
                margin: 0 2px;
                background-color: #bbb;
                border-radius: 50%;
                display: inline-block;
                transition: background-color 0.6s ease;
            }

            .active, .dot:hover {
                background-color: #717171;
            }

            /* Fading animation */
            .fade {
                -webkit-animation-name: fade;
                -webkit-animation-duration: 1.5s;
                animation-name: fade;
                animation-duration: 1.5s;
            }

            @-webkit-keyframes fade {
                from {
                    opacity: .4
                }
                to {
                    opacity: 1
                }
            }

            @keyframes fade {
                from {
                    opacity: .4
                }
                to {
                    opacity: 1
                }
            }

            /* On smaller screens, decrease text size */
            @media only screen and (max-width: 300px) {
                .prev, .next, .text {
                    font-size: 11px
                }
            }
        </style>

        <title><fmt:message key="proposta"/></title>
    </head>
    <body>
    <%
        String contextPath = request.getContextPath().replace("/", "");
    %>

    <h2>
        <c:if test="${sessionScope.clienteLogado == null and sessionScope.lojaLogado == null}">
            &nbsp;&nbsp;&nbsp;
            <c:redirect url="/veiculos/noAuth"/>
        </c:if>
    </h2>

    <div style="text-align: center">
        <c:if test="${sessionScope.clienteLogado != null}">
            <h2>
                <a href="/<%=contextPath%>"><fmt:message key="homepage"/></a>
            </h2>
        </c:if>
        <c:if test="${sessionScope.lojaLogado != null}">
            <h2>
                <a href="/<%=contextPath%>"><fmt:message key="homepage"/></a>
                &nbsp;&nbsp;&nbsp;
                <a href="/<%=contextPath%>/propostas"><fmt:message key="lista_propostas"/></a>
            </h2>
        </c:if>
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
    <div class="tudo">
        <div class="slideshow-container">

            <c:forEach var="foto" items="${requestScope.arrayNumFotos}">
                <div class="mySlides fade">
                    <div class="numbertext">${foto} / ${requestScope.numFotosVeiculo}</div>
                    <img src="${pageContext.request.contextPath}/fotos/${requestScope.veiculo.chassi}_${foto}.jpg"
                         style="width: 100%">
                </div>
            </c:forEach>

            <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
            <a class="next" onclick="plusSlides(1)">&#10095;</a>
        </div>

        <div style="text-align: center" class="formulario">
            <c:choose>
                <c:when test="${requestScope.proposta != null}">
                    <form action="atualiza" method="post">
                        <%@include file="campos.jsp" %>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="insere" method="post">
                        <%@include file="campos.jsp" %>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script>
        var slideIndex = 1;
        showSlides(slideIndex);

        function plusSlides(n) {
            showSlides(slideIndex += n);
        }

        function currentSlide(n) {
            showSlides(slideIndex = n);
        }

        function showSlides(n) {
            var i;
            var slides = document.getElementsByClassName("mySlides");
            var dots = document.getElementsByClassName("dot");
            if (n > slides.length) {
                slideIndex = 1
            }
            if (n < 1) {
                slideIndex = slides.length
            }
            for (i = 0; i < slides.length; i++) {
                slides[i].style.display = "none";
            }
            for (i = 0; i < dots.length; i++) {
                dots[i].className = dots[i].className.replace(" active", "");
            }
            slides[slideIndex - 1].style.display = "block";
            dots[slideIndex - 1].className += " active";
        }
    </script>
    </body>
</fmt:bundle>
</html>
