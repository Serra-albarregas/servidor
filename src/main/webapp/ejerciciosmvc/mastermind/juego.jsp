<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.utilidades.com/jstlfn" prefix="ut" %>
<%@ page import="com.serra.beans.Mastermind" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mastermind</title>
</head>
<body>
    <c:set var="palabra" value="MASTERMIND" />
    <c:set var="colores" value="${ut:coloresAleatorios(fn:length(palabra))}" />

    <c:forEach var="color" items="${colores}" varStatus="status">
        <c:set var="letra" value="${fn:substring(palabra, status.index, status.index + 1)}" />
        <div style="width:50px; height:50px; margin-bottom:20px; background-color:${color}; display:inline-block; text-align:center; line-height:50px; font-weight:bold;">
            ${letra}
        </div>
    </c:forEach>

    <p>Jugando con ${sessionScope.juego.nDigitos} digitos</p>

    <c:if test="${not empty mensaje}" >
        <p>${mensaje}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/mastermindServlet" method="post">
        <label for="intento">Adivina el número</label>
        <input type="number" id="intento" name="intento" autofocus
            ${ganar ? "disabled" : ""} >
        <input type="submit" value="Adivina"
            ${ganar ? "disabled" : ""} >
    </form>

    <c:if test="${ganar}" >
        <p>HAS GANADO</p>
        <form action="${pageContext.request.contextPath}/mmSetupServlet" method="post">
            <input type="hidden" name="nDigitos" id="nDigitos" value="${sessionScope.juego.nDigitos}">
            <input type="submit" value="Repetir con ${sessionScope.juego.nDigitos}">
        </form>
        <form action="ejerciciosmvc/mastermind/inicio.jsp">
            <input type="submit" value="Volver a Jugar">
        </form>
    </c:if>

    <c:choose>
       <c:when test="${fn:length(sessionScope.juego.intentos) > 0}">
        <table>
            <thead>
                <th>Numero</th>
                <th>Intento</th>
                <th>Heridos</th>
                <th>Muertos</th>
            </thead>
            <tbody>
                <c:forEach items="${sessionScope.juego.intentos}" var="intento" varStatus="status">
                    <tr>
                    <td>${status.count}</td>
                    <td>${intento.key}</td>
                    <td>${intento.value[0]}</td>
                    <td>${intento.value[1]}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
       </c:when>
       <c:otherwise>
        <p>Todavía no se han hecho intentos</p>
       </c:otherwise>
    </c:choose>
</body>
</html>