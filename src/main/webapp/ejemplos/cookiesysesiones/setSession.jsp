<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear sesión</title>
</head>
<body>
<%
    session.setMaxInactiveInterval(600);                    // Tiempo de expiración de sesión: 10 minutos (600 segundos)
    session.setAttribute("usuario", "Alfredo");
    
%>
    <h3>Sesion creada correctamente <%=session.getId()%></h3>
</body>
</html>

