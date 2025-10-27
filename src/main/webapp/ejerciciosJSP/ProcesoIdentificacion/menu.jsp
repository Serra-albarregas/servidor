<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false"%>
<%@ page import="com.serra.utils.CookieUtils"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menú de la aplicación</title>
</head>
<body>
    <%
    HttpSession session = request.getSession(false);
    String mensaje = "";
    if (session==null){
        mensaje = "Error al cargar la sesión";
        response.sendRedirect("login.jsp?"+mensaje);
        return;
    }
    String nombre = (String) session.getAttribute("usuario");
    if (nombre==null){
        mensaje = "Error al cargar los datos";
        response.sendRedirect("login.jsp?mensaje"+mensaje);
    }
    %>
    <h1>Menú de administración de <%=nombre%></h1>
    <%
        Cookie ultimoAcceso = CookieUtils.getCookie(request, "ultimoAcceso");
        if (ultimoAcceso == null) {        
    %>
            <p>Es la primera vez que entras en la página, bienvenido</p>
    <%
        } else {
            LocalDateTime fecha = LocalDateTime.parse(ultimoAcceso.getValue());
            DateTimeFormatter formatterSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String fechaFormateada = fecha.format(formatterSalida);
    %>
    <p>Ultimo acceso <%=fechaFormateada%></p>
    <%
        }
    %>
    <p>Varias opciones</p>
    <form action="loginOUT.jsp" method="post">
        <input type="submit" value="Cerrar sesión">
    </form>
</body>
</html>