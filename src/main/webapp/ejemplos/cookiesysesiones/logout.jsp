<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cerrar sesión</title>
</head>
<body>
<%
    HttpSession session = request.getSession(false);
    if (session != null) {
        session.invalidate();
%>
        <p>Sesión cerrada correctamente.</p>
<%
    } else {
%>
        <p>No había sesión activa.</p>
<%
    }
%>

</body>
</html>