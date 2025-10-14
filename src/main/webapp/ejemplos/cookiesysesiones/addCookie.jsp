<%@ page import="javax.servlet.http.Cookie" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Añadir cookie</title>
</head>
<body>
    <%
    // Crear una cookie
    Cookie cookie = new Cookie("Clave", "Valor");
    
    // Establecer duración: 1 hora
    cookie.setMaxAge(60 * 60);
    
    // Añadir la cookie a la respuesta
    response.addCookie(cookie);
%>
    <h3>Cookie creada correctamente</h3>
</body>
</html>

