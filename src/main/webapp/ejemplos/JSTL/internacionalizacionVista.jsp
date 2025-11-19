<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.serra.beans.Usuario"%>

<c:choose>
   <c:when test="${not empty sessionScope.lang}">
        <c:set var="lang" value="${sessionScope.lang}" />
   </c:when>
   <c:otherwise>
        <c:set var="lang" value="${not empty cookie.lang ? cookie.lang.value : 'es_ES'}" />
   </c:otherwise>
</c:choose>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="internacionalizacion.mensajes" />

<!DOCTYPE html>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="label.title"/></title>
</head>
<body>
<h1><fmt:message key="label.title"/></h1>

<c:if test="${not empty mensaje}">
    <c:out value="${mensaje}" />
</c:if>

<form action="internacionalizacion" method="GET">
    <label for="usuario"><fmt:message key="label.name"/></label>
    <input type="text" name="usuario" id="usuario">

    <label for="idioma"><fmt:message key="label.language"/></label>
    <select name="idioma" id="idioma">
        <option value="es_ES" ${lang == 'es_ES' ? 'selected' : ''}><fmt:message key="lang.es_ES"/></option>
        <option value="es_MX" ${lang == 'es_MX' ? 'selected' : ''}><fmt:message key="lang.es_MX"/></option>
        <option value="en_GB" ${lang == 'en_GB' ? 'selected' : ''}><fmt:message key="lang.en_GB"/></option>
        <option value="fr_FR" ${lang == 'fr_FR' ? 'selected' : ''}><fmt:message key="lang.fr_FR"/></option>
        <option value="de_DE" ${lang == 'de_DE' ? 'selected' : ''}><fmt:message key="lang.de_DE"/></option>
        <option value="jp_JP" ${lang == 'jp_JP' ? 'selected' : ''}><fmt:message key="lang.jp_JP"/></option>
    </select>

    <label for="dinero"><fmt:message key="label.money"/></label>
    <input type="number" name="dinero" id="dinero">

    <input type="submit" value="<fmt:message key='label.submit'/>">
</form>

<c:if test="${not empty sessionScope.usuario and not empty sessionScope.dinero}">
    <p>
        <fmt:message key="label.user">
            <fmt:param value="${sessionScope.usuario}" />
        </fmt:message>
    </p>
    <p>
        <fmt:message key="label.balance">
            <fmt:param>
                <fmt:formatNumber value="${sessionScope.dinero}" type="currency" />
            </fmt:param>
        </fmt:message>
    </p>
</c:if>
    <p>
        <fmt:message key="label.lang">
            <fmt:param value="${lang}" />
        </fmt:message>
    </p>


    <h2><fmt:message key="label.time"/></h2>

    <p><fmt:message key="label.timeMadrid"/>: 
    <fmt:formatDate value="${hora}" pattern="HH:mm:ss" timeZone="Europe/Madrid" />
    </p>

    <p><fmt:message key="label.timeParis"/>: 
    <fmt:formatDate value="${hora}" type="date" dateStyle="short" timeZone="Europe/Paris" />
    </p>

    <p><fmt:message key="label.timeMexico"/>: 
    <fmt:formatDate value="${hora}" type="time" dateStyle="medium" timeZone="America/Mexico_City" />
    </p>

    <p><fmt:message key="label.timeCairo"/>: 
    <fmt:formatDate value="${hora}" type="both" dateStyle="long" timeZone="Africa/Cairo" />
    </p>

        <p><fmt:message key="label.timeTokyo"/>: 
    <fmt:formatDate value="${hora}" type="both" dateStyle="full" timeZone="Asia/Tokyo" />
    </p>
</body>
</html>
