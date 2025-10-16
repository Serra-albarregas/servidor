<%@ page import="javax.servlet.http.Cookie" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Obtener cookies</title>
</head>
<body>
<%
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
        for (Cookie c : cookies) {
%>
            <p>Cookie clave <%=c.getName()%> Valor <%=c.getValue()%></p>
<%
        }
    }
%>
</body>
</html>


