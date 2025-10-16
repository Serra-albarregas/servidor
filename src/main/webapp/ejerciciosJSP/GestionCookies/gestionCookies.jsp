<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de cookies</title>
</head>
<body>
    <%-- FORMULARIO --%>
<%
    String accion = request.getParameter("accion");
    String actualizar = request.getParameter("actualizar");
    String nombre = request.getParameter("nombre")!=null?request.getParameter("nombre"):"";
    String valor = request.getParameter("valor")!=null?request.getParameter("valor"):"";
%>
    <form action="gestionCookies.jsp" method="get">
        <input type="hidden" name="actualizar" value="true">
        <input type="hidden" name="accion" value="<%= accion %>">
        <input type="text" name="nombre" id="nombre" <%="modificar".equals(accion)?"readonly":""%> value="<%= "modificar".equals(accion) ? nombre : "" %>">
        <input type="text" name="valor" id="valor" value="<%= "modificar".equals(accion) ? valor : "" %>">
        <input type="submit" value="<%="modificar".equals(accion)?"Modificar cookie":"Crear cookie"%>">
    </form>

    <%-- LISTA --%>

    <table>
<%
    Cookie[] cookies = request.getCookies();
    if (cookies!=null) {
        for(Cookie c : cookies) {
%>
            <tr>
            <td><%= c.getName() %></td>
            <td><%= c.getValue() %></td>
            <td><form action="gestionCookies.jsp" method="get">
                <input type="hidden" name="accion" value="modificar">
                <input type="hidden" name="nombre" value="<%= c.getName() %>">
                <input type="hidden" name="valor" value="<%= c.getValue() %>">
                <input type="submit" value="modificar">
            </form></td>
            <td><form action="gestionCookies.jsp" method="get">
                <input type="hidden" name="accion" value="eliminar">
                <input type="hidden" name="actualizar" value="true">
                <input type="hidden" name="nombre" value="<%= c.getName() %>">
                <input type="hidden" name="valor" value="<%= c.getValue() %>">
                <input type="submit" value="eliminar">
            </form></td>
            </tr>
<%
        }
    }
%>
    </table>

    <%-- CONFIRMACIÓN --%>

<%
    if ("true".equals(actualizar)) {
        if (nombre.equals("") || valor.equals("")) {
%>
            <p>Error - los campos no deben estar vacíos</p>
<%
        } else {
            Cookie cookie = new Cookie(nombre, valor);
            if ("eliminar".equals(accion)) {
                cookie.setMaxAge(0);
            }
            response.addCookie(cookie);
            if ("eliminar".equals(accion)) {
%>
                <p>Cookie <%= nombre %> eliminada</p>
<%
            }else if ("modificar".equals(accion)){
%>
                <p>Cookie <%= nombre %> modificada con valor <%= valor %></p>
<%
            }else{
%>
                <p>Cookie <%= nombre %> creada con valor <%= valor %></p>
<%
            }
        }
%>
            <a href="gestionCookies.jsp">Aceptar</a>
<%
    }
%>
</body>
</html>