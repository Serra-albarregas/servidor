<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.utilidades.com/jstlfn" prefix="ut" %>

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

    <c:if test="${not empty mensaje}" >
        <p>${mensaje}</p>
    </c:if>
    <form action="${pageContext.request.contextPath}/mmSetupServlet" method="post">
        <label for="nDigitos">Número de dígitos</label>
        <input type="number" name="nDigitos" id="nDigitos" autofocus>
        <input type="submit" value="Comenzar juego">
    </form>
</body>
</html>