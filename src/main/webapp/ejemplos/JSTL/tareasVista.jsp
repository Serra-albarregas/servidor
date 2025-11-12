<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>Gestor de Tareas</title>
</head>
<body>

<h2>Bienvenido, ${sessionScope.usuario}</h2>

<!-- Mensaje temporal (request) -->
<c:if test="${not empty mensaje}">
    <p style="color:green;">${mensaje}</p>
</c:if>

<!-- Formulario para crear tareas -->
<h3>Agregar nueva tarea</h3>
<form method="post" action="${pageContext.request.contextPath}/tareasServlet">
    <label>Título:</label><br>
    <input type="text" name="titulo" required><br><br>

    <label>Descripción:</label><br>
    <textarea name="descripcion" rows="3" cols="40"></textarea><br><br>

    <label>
        <input type="checkbox" name="completada"> ¿Completada?
    </label><br><br>

    <button type="submit">Agregar</button>
</form>

<hr>

<!-- Mostrar lista de tareas -->
<c:set var="tareas" value="${sessionScope.tareas}" />
<c:set var="total" value="${fn:length(tareas)}" />

<h3>Tareas guardadas (${total}):</h3>

<c:choose>
    <c:when test="${total > 0}">
        <table border="1" cellpadding="6" cellspacing="0">
            <tr>
                <th>#</th>
                <th>Título</th>
                <th>Descripción</th>
                <th>Estado</th>
            </tr>

            <c:forEach var="tarea" items="${tareas}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${fn:toUpperCase(tarea.titulo)}</td>
                    <td>${tarea.descripcion}</td>
                    <c:choose>
                        <c:when test="${tarea.completada}">
                            <td style="color:green;">Completada</td>
                        </c:when>
                        <c:otherwise>
                            <td style="color:red;">Pendiente</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </c:when>

    <c:otherwise>
        <p>No hay tareas todavía.</p>
    </c:otherwise>
</c:choose>

<!-- Ejemplo de fn:join y split -->
<c:set var="frutas" value="${fn:split('Manzana,Naranja,Plátano', ',')}" />
<p>Frutas del día: ${fn:join(frutas, ' 🍎 ')}</p>

<!-- Ejemplo de c:remove -->
<c:set var="temp" value="texto temporal" />
<p>Variable temporal: ${temp}</p>
<c:remove var="temp" />
<p>¿Sigue existiendo? ${empty temp ? 'No' : 'Sí'}</p>

</body>
</html>
