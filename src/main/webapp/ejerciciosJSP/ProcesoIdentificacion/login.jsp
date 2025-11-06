<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.serra.utils.CookieUtils"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
</head>
<body>
    <%
        String mensaje = request.getParameter("mensaje")!=null?request.getParameter("mensaje"):"";
        Cookie rememberCookie = CookieUtils.getCookie(request, "remember");
        String rememberedUser = rememberCookie!=null?rememberCookie.getValue():null;
    %>
    <i><%=mensaje%></i>
    <form action="loginIN.jsp" method="post">
    <p>
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre"
        <% if (rememberCookie!=null)%> value="<%=rememberedUser%>"
        >
    </p><p>
        <label for="pass">Contraseña</label>
        <input type="password" id="pass" name="pass">
    </p><p>
        <label for="remember">Recordar usuario</label>
        <input type="checkbox" id="remember" name="remember"
        <% if (rememberCookie!=null)%> checked
        >
    </p>
        <input type="submit" value="Iniciar sesión">
    </form>
</body>
</html>